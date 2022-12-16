package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.*;
import com.golubovich.project_trpo_tofi.repository.ResponseRepository;
import com.golubovich.project_trpo_tofi.service.api.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private final RequestServiceImpl requestService;

    @Autowired
    public ResponseServiceImpl(ResponseRepository responseRepository, RequestServiceImpl requestService) {
        this.responseRepository = responseRepository;
        this.requestService = requestService;
    }

    public Response findByRequestId(Long requestId) {
        return responseRepository.findById(requestId).orElse(null);
    }

    public void createResponseToRequestById(Long requestId) {
        Response response = this.formResponse(requestId);
        if (response != null) {
            responseRepository.save(response);
            requestService.updateStatusCompleted(requestId);
        }
    }

    private Response formResponse(Long requestId) {
        Request request = requestService.findById(requestId);
        User user = request.getUser();

        // get user score (simple not real formula)
        int ageScore = this.getAgeScore(user.getUserDetails().getAge());
        int creditsCountScore = this.getCreditsCountScore(request.getDetails().getCreditsCount());
        BigDecimal income = request.getDetails().getIncome();
        BigDecimal creditsPayments = request.getDetails().getCreditsPayments();
        int moneyScore = this.getMoneyScore(income.subtract(creditsPayments));

        int userScore = 150 + 25 * (ageScore + creditsCountScore + moneyScore);
        int minScore = request.getCreditTermRateVariant().getCredit().getBank().getTrustZone();

        // bank reject request if users' credits payments more than income before this new credit
        if (moneyScore == -1) {
            request.setRequestStatus(RequestStatus.REJECTED);
            requestService.update(request);
            return null;
        }

        BigDecimal creditMaxSum25;
        creditMaxSum25 = request.getCreditTermRateVariant().getCredit().getMaxSum()
                .divide(BigDecimal.valueOf(4), RoundingMode.HALF_EVEN);

        // bank return negative answer if user has low score or choose low sum (<25% from maxCreditSum)
        if ((userScore < minScore) || (request.getSum().compareTo(creditMaxSum25) < 0)) {
            return new Response(BigDecimal.valueOf(0), BigDecimal.valueOf(0), new Date(), request);
        }

        // РЕП - размер ежемесячного платежа (sum / term) + (sum * rate%) / (12 * 100)
        BigDecimal REP = this.countResponsePayments(
                request.getCreditTermRateVariant().getTerm(),
                request.getCreditTermRateVariant().getRate(),
                request.getSum()
        );
        BigDecimal allPayments = REP.add(creditsPayments);
        // ПДН - показатель долговой нагрузки  (allCreditsPayments / ЗП * 100)
        double PDN = allPayments
                .divide(income, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();


        // bank try to recommend other credit variant or return negative answer if users' PDN > 40%
        if (PDN > 40) {
            List<CreditTermRateVariant> allVariants = new ArrayList<>(
                    request.getCreditTermRateVariant().getCredit().getCreditTermRateVariants()
            );
            allVariants.remove(request.getCreditTermRateVariant());

            if (allVariants.size() != 0) {
                for (CreditTermRateVariant variant : allVariants) {
                    REP = this.countResponsePayments(
                            request.getCreditTermRateVariant().getTerm(),
                            request.getCreditTermRateVariant().getRate(),
                            request.getSum()
                    );
                    allPayments = REP.add(creditsPayments);
                    PDN = allPayments
                            .divide(income, RoundingMode.HALF_EVEN)
                            .subtract(BigDecimal.valueOf(100))
                            .round(new MathContext(2))
                            .doubleValue();
                    if (PDN < 40) {
                        return new Response(request.getSum(),
                                REP.setScale(2, RoundingMode.HALF_EVEN), new Date(), request, variant);
                    }
                }
            } else {
                return new Response(BigDecimal.valueOf(0), BigDecimal.valueOf(0), new Date(), request);
            }
        }

        return new Response(request.getSum(),
                REP.setScale(2, RoundingMode.HALF_EVEN), new Date(), request);
    }


    private BigDecimal countResponsePayments(String termStr, double rate, BigDecimal requestSum) {
        int termInMonth;
        if (termStr.contains("лет")) {
            termInMonth = Integer.parseInt(termStr.replaceAll("[^0-9]", "")) * 12;
        } else {
            termInMonth = Integer.parseInt(termStr.replaceAll("[^0-9]", ""));
        }

        // (sum / term) + (sum * rate%) / (12 * 100)
        return requestSum.divide(BigDecimal.valueOf(termInMonth), RoundingMode.HALF_EVEN)
                .add(
                        (requestSum.multiply(BigDecimal.valueOf(rate)))
                                .divide(BigDecimal.valueOf(1200), RoundingMode.HALF_EVEN)
                );
    }

    private int getAgeScore(int userAge) {
        if (userAge < 37 || userAge > 52) {
            return 0;
        }
        return 1;
    }

    private int getCreditsCountScore(int creditsCount) {
        return switch (creditsCount) {
            case 0 -> 4;
            case 1 -> 3;
            case 2 -> 2;
            case 3 -> 1;
            default -> 0;
        };
    }

    private int getMoneyScore(BigDecimal money) {
        if (money.compareTo(BigDecimal.valueOf(0)) < 0) {
            return -1;
        }
        if (money.compareTo(BigDecimal.valueOf(500)) < 0) {
            return 0;
        }
        if (money.compareTo(BigDecimal.valueOf(1000)) < 0) {
            return 1;
        }
        if (money.compareTo(BigDecimal.valueOf(2000)) < 0) {
            return 2;
        }
        if (money.compareTo(BigDecimal.valueOf(4000)) < 0) {
            return 3;
        }
        if (money.compareTo(BigDecimal.valueOf(6000)) < 0) {
            return 4;
        }
        return 5;
    }
}

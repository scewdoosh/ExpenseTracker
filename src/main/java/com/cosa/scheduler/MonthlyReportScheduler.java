package com.cosa.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosa.model.Payment;
import com.cosa.model.UserModel;
import com.cosa.repo.IPaymentRepo;
import com.cosa.repo.IUserModelRepo;
import com.cosa.service.DiscordService;

@Component
@EnableScheduling
public class MonthlyReportScheduler {

    @Autowired
    private IUserModelRepo userRepository;

    @Autowired
    private IPaymentRepo paymentRepository;

    @Autowired
    private DiscordService discordService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void sendMonthlyReports() {
        List<UserModel> users = userRepository.findAll();

        for (UserModel user : users) {
            if (user.getDiscordWebhook() == null) continue;

            LocalDate lastMonth = LocalDate.now().minusMonths(1);
            int year = lastMonth.getYear();

            Payment payment = paymentRepository.findByUserModel(user).orElse(null);
            if (payment == null) continue;

            String message = "📊 **Monthly Report - " + lastMonth.getMonth() + " " + year + "**\n" +
                    "👤 " + user.getName() + "\n" +
                    "💰 Total Spent: ₹" + payment.getTotalAmount();

            discordService.sendReport(user.getDiscordWebhook(), message);
        }
    }
}
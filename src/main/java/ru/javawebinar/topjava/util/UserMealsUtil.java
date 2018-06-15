package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds=new ArrayList<>();

        Map<LocalDate,Integer> summColoriesInDay=caloriesInDay(mealList);
/*
        for (UserMeal userMeal:mealList){
            if((userMeal.getDateTime().toLocalTime().isAfter(startTime)) && userMeal.getDateTime().toLocalTime().isBefore(endTime)){
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        summColoriesInDay.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
            }
        }
*/

        mealList.forEach((userMeal)->{
            if((userMeal.getDateTime().toLocalTime().isAfter(startTime)) && userMeal.getDateTime().toLocalTime().isBefore(endTime)){
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        summColoriesInDay.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
            }
        });


        return userMealWithExceeds;
    }

    private static Map<LocalDate,Integer> caloriesInDay(List<UserMeal> mealList){
        Map<LocalDate,Integer> summColoriesInDay=new HashMap<>();
        for (UserMeal userMeal:mealList){
            LocalDate localDate=userMeal.getDateTime().toLocalDate();
            if(summColoriesInDay.get(localDate)==null){
                summColoriesInDay.put(localDate,userMeal.getCalories());
            }else{
                summColoriesInDay.put(localDate,summColoriesInDay.get(localDate)+userMeal.getCalories());
            }
        }

        return summColoriesInDay;
    }
}

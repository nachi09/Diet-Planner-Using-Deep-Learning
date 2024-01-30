package com.example.dietplanner.utils;

import com.example.dietplanner.models.UserInfoModel;

public class Convertors {
     private static final Float ACTIVITY_LEVEL_SEDENTARY=1.2F;
     private static final Float ACTIVITY_LEVEL_LITTLE=1.375F;
     private static final Float ACTIVITY_LEVEL_MODERATE=1.55F;
     private static final Float ACTIVITY_LEVEL_HARD=1.725F;
     private static final Float ACTIVITY_LEVEL_VERY_HARD=1.9F;


     public static Integer BMR(UserInfoModel userInfoModel){
          //weight in KG
          // height in CM
          if (userInfoModel.getGender().equals("Male")){
               return Math.toIntExact(Math.round(10 * userInfoModel.getWeightInKg() + 6.25 * (userInfoModel.getHeightInCM()) - 5 * userInfoModel.getAge() + 5));
          }
          return Math.toIntExact(Math.round(10 * userInfoModel.getWeightInKg() + 6.25 * (userInfoModel.getHeightInCM()) - 5 * userInfoModel.getAge() - 161));

     }
//   Harris Benedict Formula
     public static Integer caloriesToMaintain(UserInfoModel userInfoModel){
          int dailyCalories=0;
          switch (userInfoModel.getActivityLevel()){
               case 1:
                    dailyCalories = Math.round(BMR(userInfoModel)*ACTIVITY_LEVEL_SEDENTARY);
                    break;
               case 2:
                    dailyCalories = Math.round(BMR(userInfoModel)*ACTIVITY_LEVEL_LITTLE);
                    break;
               case 3:
                    dailyCalories = Math.round(BMR(userInfoModel)*ACTIVITY_LEVEL_MODERATE);
                    break;
               case 4:
                    dailyCalories = Math.round(BMR(userInfoModel)*ACTIVITY_LEVEL_HARD);
                    break;
               case 5:
                    dailyCalories = Math.round(BMR(userInfoModel)*ACTIVITY_LEVEL_VERY_HARD);
                    break;
          }
          return dailyCalories;
     }


     public static double footToCM(double foot){
          return 30.48*foot;
     }

     public static String requiredProtein(String calories){
          int calorie = Integer.parseInt(calories);
          double m = calorie * 0.3;
          double protein = Math.floor(m/4);
          return String.valueOf(protein);

     }
     public static String requiredCarbs(String calories){
          int calorie = Integer.parseInt(calories);
          double m = calorie * 0.4;
          double carbs = Math.floor(m/4);
          return String.valueOf(carbs);

     }
     public static String requiredFats(String calories){
          int calorie = Integer.parseInt(calories);
          double m = calorie * 0.3;
          double fats = Math.floor(m/9);
          return String.valueOf(fats);
     }


     public static String calorieAccToGoal(String calories,String goal){

          int calorie = Integer.parseInt(calories);
          if(goal.equals("Gain")){
               calorie+=500;
          }else {
               calorie-=500;
          }
          return String.valueOf(calorie);
     }

//     public static Integer caloriesToGain(){
//
//     }

}

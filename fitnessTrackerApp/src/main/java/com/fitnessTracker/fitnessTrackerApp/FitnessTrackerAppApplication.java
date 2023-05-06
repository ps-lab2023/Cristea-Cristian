package com.fitnessTracker.fitnessTrackerApp;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FitnessTrackerAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(FitnessTrackerAppApplication.class, args);
	}
	@Bean
	CommandLineRunner init(WorkoutRecordService workoutRecordService, UserService userService,
						   WorkoutPlanService workoutPlanService,
						   PlanDayService planDayService,
						   PlanEntryService planEntryService,
						   UserPlanService userPlanService) {
		return args -> {
			//System.out.println(workoutRecordService.getWorkoutRecordsByUser(9));
		};
	}
////		return args -> {
////			AddUserDTO newUser = AddUserDTO.builder().username("cristiancristea19").password("sad").weight(75).gender(GenderEnum.M).role(UserRoleEnum.USER).name("Cristi").email("cd@asd.com").build();
////			UserDTO user = userService.addUser(newUser);
////
////			System.out.println(userService.getAllUsers());
////			System.out.println(userService.logIn("cristiancristea19", "sad"));
////			EditUserDTO editUser = EditUserDTO.builder()
////					.id(user.getId())
////					.name("Cristi")
////					.password("sadd")
////					.height(156)
////					.weight(75)
////					.build();
////
////			userService.updateUser(editUser);
////			System.out.println(userService.getAllUsers());
////			System.out.println(userService.getUserRole(user.getId()));
////
////
////			AddWorkoutRecordDTO workoutRecord = AddWorkoutRecordDTO.builder()
////					.activityType(ActivityTypeEnum.WALKING)
////					.distance(12.3)
////					.calories(123)
////					.userId(user.getId())
////					.duration(new DurationDTO(1, 2, 23))
////					.build();
////			workoutRecordService.addWorkoutRecord(workoutRecord);
////			System.out.println(workoutRecordService.getWorkoutRecordById(1L));
////
////			EditWorkoutRecordDTO editWorkoutRecord = EditWorkoutRecordDTO.builder()
////					.id(1L)
////					.activityType(ActivityTypeEnum.RUNNING)
////					.distance(1.3)
////					.calories(145)
////					.duration(new DurationDTO(1, 3, 23))
////					.build();
////
////			workoutRecordService.updateWorkoutRecord(editWorkoutRecord);
////			System.out.println(workoutRecordService.getWorkoutRecordById(1L));
////			System.out.println(workoutRecordService.getWorkoutRecordsByUserId(1L));
////
//////			workoutRecordService.deleteWorkoutRecord(1L);
//////			System.out.println(workoutRecordService.getWorkoutRecordById(1L));
////
////			//Workout Plan
////			AddUserDTO newUser2 = AddUserDTO.builder().username("username2").password("sad").weight(75).gender(GenderEnum.M).role(UserRoleEnum.TRAINER).name("Trainer1").email("trainer@asd.com").build();
////			UserDTO trainer1 = userService.addUser(newUser2);
////
////			AddWorkoutPlanDTO workoutPlan = AddWorkoutPlanDTO.builder()
////					.mainActivity(ActivityTypeEnum.RUNNING)
////					.level(LevelEnum.BEGINNER)
////					.description("plan1")
////					.duration(new DurationDTO(1, 2, 3))
////					.trainerId(trainer1.getId())
////					.build();
////
////			WorkoutPlanDTO addedWorkoutPlan= workoutPlanService.addWorkoutPlan(workoutPlan);
////
////			System.out.println(workoutPlanService.getWorkoutPlanById(addedWorkoutPlan.getId()));
////			System.out.println(workoutPlanService.getWorkoutPlansByTrainerId(trainer1.getId()));
////
////			EditWorkoutPlanDTO editWorkoutPlan = EditWorkoutPlanDTO.builder()
////					.mainActivity(ActivityTypeEnum.RUNNING)
////					.level(LevelEnum.BEGINNER)
////					.description("plan1")
////					.duration(new DurationDTO(1, 2, 3))
////					.id(addedWorkoutPlan.getId())
////					.build();
////
////			workoutPlanService.updateWorkoutPlan(editWorkoutPlan);
////			System.out.println(workoutPlanService.getWorkoutPlanById(addedWorkoutPlan.getId()));
////
////			AddPlanDayDTO addPlanDay = AddPlanDayDTO.builder()
////					.planId(addedWorkoutPlan.getId())
////					.dayNumber(1)
////					.isRestDay(false)
////					.build();
////
////			PlanDayDTO planDay = planDayService.addPlanDay(addPlanDay);
////
////			System.out.println(planDayService.getPlanDayById(planDay.getId()));
////
////			EditPlanDayDTO editPlanDay = EditPlanDayDTO.builder()
////					.id(1L)
////					.dayNumber(1)
////					.isRestDay(true)
////					.build();
////
////			planDayService.updatePlanDay(editPlanDay);
////			System.out.println(planDayService.getPlanDayById(planDay.getId()));
//////			planDayService.deletePlanDay(planDay.getId());
//////			System.out.println(planDayService.getPlanDayById(planDay.getId()));
////
////			AddPlanEntryDTO addPlanEntry = AddPlanEntryDTO.builder()
////					.planDayId(planDay.getId())
////					.description("hard exercise")
////					.nameOfExercise("squats")
////					.orderNumber(2)
////					.noOfReps(30)
////					.duration(new DurationDTO(0, 2,3))
////					.build();
////
////			PlanEntryDTO planEntry = planEntryService.addPlanEntry(addPlanEntry);
////
////			System.out.println(planEntryService.getPlanEntryById(planEntry.getId()));
////
////			EditPlanEntryDTO editPlanEntry = EditPlanEntryDTO.builder()
////					.id(planEntry.getId())
////					.description("easy exercise")
////					.noOfReps(14)
////					.nameOfExercise("squats")
////					.orderNumber(2)
////					.duration(new DurationDTO(0, 2, 1))
////					.build();
////
////			planEntryService.updatePlanEntry(editPlanEntry);
////
////			System.out.println(planEntryService.getPlanEntryById(planEntry.getId()));
////
//////			planEntryService.deletePlanEntry(planEntry.getId());
//////			System.out.println(planEntryService.getPlanEntryById(planEntry.getId()));
////
////			AddUserPlanDTO addUserPlan = AddUserPlanDTO.builder()
////					.userId(user.getId())
////					.planId(addedWorkoutPlan.getId())
////					.build();
////
////			UserPlanDTO userPlan = userPlanService.addUserPlan(addUserPlan);
////
////			System.out.println(userPlanService.getUserPlanById(userPlan.getId()));
////
////			EditUserPlanDTO editUserPlan = EditUserPlanDTO.builder()
////					.id(userPlan.getId())
////					.currentDay(3)
////					.build();
////
////			userPlanService.updateUserPlan(editUserPlan);
////			System.out.println(userPlanService.getUserPlanById(userPlan.getId()));
////
//////			userPlanService.deleteUserPlan(userPlan.getId());
//////			System.out.println(userPlanService.getUserPlanById(userPlan.getId()));
////		};


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

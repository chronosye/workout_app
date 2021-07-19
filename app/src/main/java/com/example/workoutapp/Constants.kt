package com.example.workoutapp

class Constants {
    //exercises for our application
    companion object {
        fun defaultExerciseList(): ArrayList<ExerciseModel> {
            var exerciseList = ArrayList<ExerciseModel>()

            val burpees = ExerciseModel(1, "Burpees", R.drawable.burpees, false, false)
            exerciseList.add(burpees)

            val climbers = ExerciseModel(2, "Climbers", R.drawable.climbers, false, false)
            exerciseList.add(climbers)

            val handRotation = ExerciseModel(3, "Hand rotation", R.drawable.hand_rotation, false, false)
            exerciseList.add(handRotation)

            val highKnees = ExerciseModel(4, "High knees", R.drawable.high_knees, false, false)
            exerciseList.add(highKnees)

            val kneeToElbow = ExerciseModel(5, "Knee to elbow", R.drawable.knee_to_elbow, false, false)
            exerciseList.add(kneeToElbow)

            val sitUp = ExerciseModel(6, "Sit up", R.drawable.sit_up, false, false)
            exerciseList.add(sitUp)

            val squats = ExerciseModel(7, "Squats", R.drawable.squats, false, false)
            exerciseList.add(squats)

            return exerciseList
        }
    }
}
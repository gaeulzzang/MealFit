package com.example.mealfit

import com.google.gson.annotations.SerializedName

data class FoodList(
    @SerializedName("I2790") val list: FoodDto
)

data class FoodDto(
    @SerializedName("row") val foodInfo: List<FoodInfo>
)

data class FoodInfo(
    @SerializedName("DESC_KOR") val foodName: String,
    @SerializedName("NUTR_CONT1") val foodCalorie: String,
    @SerializedName("NUTR_CONT2") val foodCarbohydrate: String,
    @SerializedName("NUTR_CONT3") val foodProtein: String,
    @SerializedName("NUTR_CONT4") val foodFat: String
)

//    @SerializedName("NUM") val num: Int,
//    @SerializedName("FOOD_CD") val food_cd: String,
//    @SerializedName("SAMPLING_REGION_NAME") val sampling_region_name: String,
//    @SerializedName("SAMPLING_MONTH_NAME") val sampling_month_name: String,
//    @SerializedName("SAMPLING_REGION_CD") val sampling_region_cd: String,
//    @SerializedName("SAMPLING_MONTH_CD") val sampling_month_cd: String,
//    @SerializedName("GROUP_NAME") val group_name: String,
//    @SerializedName("DESC_KOR") val food_name: String,
//    @SerializedName("RESEARCH_YEAR") val research_year: String,
//    @SerializedName("MAKER_NAME") val maker_name: String,
//    @SerializedName("SUB_REF_NAME") val sub_ref_name: String,
//    @SerializedName("SERVING_SIZE") val serving_size: String,
//    @SerializedName("SERVING_UNIT") val serving_unit: String,
//    @SerializedName("NUTR_CONT1") val food_calorie: String,
//    @SerializedName("NUTR_CONT2") val food_carbohydrate: String,
//    @SerializedName("NUTR_CONT3") val food_protein: String,
//    @SerializedName("NUTR_CONT4") val food_fat: String,
//    @SerializedName("NUTR_CONT5") val nutr_cont5: String,
//    @SerializedName("NUTR_CONT6") val nutr_cont6: String,
//    @SerializedName("NUTR_CONT7") val nutr_cont7: String,
//    @SerializedName("NUTR_CONT8") val nutr_cont8: String,
//    @SerializedName("NUTR_CONT9") val nutr_cont9: String

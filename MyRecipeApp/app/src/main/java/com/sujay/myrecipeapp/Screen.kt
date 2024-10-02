package com.sujay.myrecipeapp

sealed class Screen(val route:String) {
    object  RecipeScreen:Screen("recipescreen")
    object DetailScreen:Screen("detailscreen")

}

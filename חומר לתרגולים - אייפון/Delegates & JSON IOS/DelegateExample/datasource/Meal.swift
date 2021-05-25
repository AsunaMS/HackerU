//
//  Meal.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import Foundation

struct Meal : Codable {
    let mealName:String?
    let mealImage:String?
    let mealId:String?
    
    enum CodingKeys : String, CodingKey {
        case mealName = "strMeal", mealImage = "strMealThumb", mealId = "idMeal"
    }
}

//
//  ApiManager.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import UIKit
struct Meal : Codable{
    let strMealThumb:String?
    let strMeal:String?
}
struct ApiResponse:Codable {
    let meals:[Meal]
}
class ApiManager {
    let host = "www.themealdb.com"
    //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    func getMeals(callback:@escaping (ApiResponse) -> Void) {
        var compontents = URLComponents()
        compontents.host = host
        compontents.scheme = "https"
        compontents.path = "/api/json/v1/1/filter.php"
        compontents.queryItems = [URLQueryItem(name: "c", value: "Seafood")]
        guard let url  = compontents.url else {return}
        URLSession.shared.dataTask(with: url) { (data, res, err) in
            if let err = err {
                print(err.localizedDescription)
                return
            }
            guard let data = data else {
                return}
            let decoder = JSONDecoder()
            guard let response  = try? decoder.decode(ApiResponse.self, from: data) else {
                return}
            callback(response)
            
        }.resume()
    }
}

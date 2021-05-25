//
//  ApiManager.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import Foundation

class ApiManager {
    let host = "www.themealdb.com"
    
    // https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    
    func getMeals(callback: @escaping (ApiResponse) -> Void) {
        var components = URLComponents()
        components.host = host
        components.scheme = "https"
        components.path = "/api/json/v1/1/filter.php"
        components.queryItems = [URLQueryItem(name: "c", value: "Seafood")]
        guard let url = components.url else {return}
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            if let error = error {
                print(error.localizedDescription)
                return
            }
            guard let data = data else {return}
            let decoder = JSONDecoder()
            guard let apiResponse = try? decoder.decode(ApiResponse.self, from: data) else {return}
            callback(apiResponse)
        }.resume()
        
   
    }
}

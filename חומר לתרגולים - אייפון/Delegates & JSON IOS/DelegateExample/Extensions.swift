//
//  Extensions.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import UIKit

extension String {
    func getImage(callback:@escaping (UIImage) -> Void) {
        DispatchQueue.global(qos: .userInteractive).async {
            guard let url = URL(string: self) else {return}
            guard let data = try? Data(contentsOf: url) else {return}
            guard let image = UIImage(data: data) else {return}
            callback(image)
        }
    }
}

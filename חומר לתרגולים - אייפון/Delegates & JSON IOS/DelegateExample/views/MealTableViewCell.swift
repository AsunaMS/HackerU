//
//  MealTableViewCell.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import UIKit

class MealTableViewCell: UITableViewCell {

    @IBOutlet weak var mealLabelView: UILabel!
    @IBOutlet weak var mealImageView: UIImageView!
    static let identifier = "MealCell"
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func populate(meal:Meal) {
        self.mealLabelView.text = meal.mealName!
        meal.mealImage?.getImage(callback: { [weak self] (image) in
            DispatchQueue.main.async {
                self?.mealImageView.image = image
            }
        })
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}

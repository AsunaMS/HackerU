//
//  MealsViewController.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import UIKit

class MealsViewController: UIViewController {
    
    @IBOutlet weak var mealTableView: UITableView!
    var meals:[Meal] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        mealTableView.delegate = self
        mealTableView.dataSource = self
        let manager:ApiManager = ApiManager()
        manager.getMeals { [weak self] (response) in
            self?.meals = response.meals
            DispatchQueue.main.async {
                self?.mealTableView.reloadData()
            }
        }
    }


}
extension MealsViewController : UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return meals.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell:MealTableViewCell = tableView.dequeueReusableCell(withIdentifier: MealTableViewCell.identifier, for: indexPath) as! MealTableViewCell
        // change the cell's properties here
        let meal = self.meals[indexPath.row]
        cell.populate(meal: meal)
        return cell
    }
    
    
}

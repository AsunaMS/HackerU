//
//  ViewController.swift
//  DelegateExample
//
//  Created by נדב אבנון on 25/05/2021.
//

import UIKit

class ViewController: UIViewController, MyProtocol {
 
    @IBOutlet weak var label: UILabel!
    func imageFetched(image: UIImage) {
        
    }
    
    
    func stringFetched(string: String) {
        label.text = string
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
  
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let vc = segue.destination as? SecondViewController {
            vc.delegate = self
        }
    }
    

}

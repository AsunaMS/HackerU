//
//  ViewController.swift
//  DelegateExample
//
//  Created by נדב אבנון on 07/03/2021.
//

import UIKit


class ViewControllerA: UIViewController {

    var initialtext:Bool = false
    
    @IBOutlet weak var mainLabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func changeText(_ sender: UIButton) {
        mainLabel?.text = initialtext ? "Controller A: Initial text" : "Controller A: The text has been changed!"
        initialtext = !initialtext
    }

    /*
    // prepare the segue for the next view controller
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    }
    */

 }


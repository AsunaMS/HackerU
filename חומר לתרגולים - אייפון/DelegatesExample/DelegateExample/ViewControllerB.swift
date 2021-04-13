//
//  SecondViewController.swift
//  DelegateExample
//
//  Created by נדב אבנון on 07/03/2021.
import UIKit


class ViewControllerB: UIViewController {
    
    @IBOutlet weak var textField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func changeMainTextAction(_ sender: UIButton) {
        // return if nothing is inserted
        guard let text = textField?.text, !text.isEmpty else {
            print("nothing was typed in")
            return}
        // do something with the text
        print(text, "was typed in")
    }
    
}

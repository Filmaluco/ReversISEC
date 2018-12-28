//
//  OrderByViewController.swift
//  IsecExamManager
//
//  Created by Ricardo Silva on 28/12/2018.
//  Copyright © 2018 Filipe Ramos Alves. All rights reserved.
//

import UIKit

class OrderByViewController: UIViewController {
    
    var extamTableViewController:ExamTableViewController?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func orderByName(_ sender: Any) {
        extamTableViewController?.orderBy = ExamTableViewController.OrderBy.name;
        dismiss(animated: true)
    }
    
    @IBAction func orderByExam(_ sender: Any) {
        extamTableViewController?.orderBy = ExamTableViewController.OrderBy.exam;
        dismiss(animated: true)
    }
    
    @IBAction func orderByYear(_ sender: Any) {
         extamTableViewController?.orderBy = ExamTableViewController.OrderBy.year;
        dismiss(animated: true)
    }
    
    @IBAction func orderBySemester(_ sender: Any) {
        extamTableViewController?.orderBy = ExamTableViewController.OrderBy.semester;
        dismiss(animated: true)
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

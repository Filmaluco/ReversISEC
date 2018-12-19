//
//  AddViewController.swift
//  IsecExamManager
//
//  Created by Filipe Ramos Alves on 19/12/2018.
//  Copyright © 2018 Filipe Ramos Alves. All rights reserved.
//

import UIKit
import CoreData

class AddViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource{
    
    @IBOutlet weak var tfName: UITextField!
    
    @IBOutlet weak var yearPicker: UIPickerView!
    let yearData = Array(1...3)
    let semesterData = Array(1...2)
    
    @IBOutlet weak var normalPicker: UIDatePicker!
    @IBOutlet weak var recursoPicker: UIDatePicker!
    @IBOutlet weak var especialPicker: UIDatePicker!
    
    //Labels for better input validation visualization
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var examNLabel: UILabel!
    @IBOutlet weak var examRLabel: UILabel!
    @IBOutlet weak var exameELabel: UILabel!
    
    var oldCourse : Any?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        yearPicker.dataSource = self
        yearPicker.delegate = self
        yearPicker.tag = 1
       
        if oldCourse == nil{
            self.title = "New Course"
        }else{
            let course = oldCourse as! Course
            self.title = "Update Course"
            let yearRow = Int(course.year - 1)
            let semesterRow = Int(course.semester - 1)
            yearPicker.selectRow(yearRow,inComponent: 0, animated: true)
            yearPicker.selectRow(semesterRow,inComponent: 1, animated: true)
            tfName.text? = course.name!
            normalPicker?.date = course.examN!
            recursoPicker?.date = course.examR!
            especialPicker?.date = course.examE!
        }
        
    }
    
    @IBAction func onSave(_ sender: Any) {
        print("Esta a tentar gravar --------------")
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        let managedContext = appDelegate.persistentContainer.viewContext
        let courseEntity = NSEntityDescription.entity(forEntityName: "Course", in: managedContext)!
        
        
        if (tfName.text?.count)! < 1 {
            nameLabel.textColor = UIColor.red
            tfName.becomeFirstResponder()
            return
        }
        
        nameLabel.textColor = UIColor.black
        
    
        //Check if data is above today
        if Date() > normalPicker.date {
            normalPicker.becomeFirstResponder()
            examNLabel.textColor = UIColor.red
            return
        }
        
        examNLabel.textColor = UIColor.black
        
        //check if recurso is above normal
        if recursoPicker.date < normalPicker.date {
            recursoPicker.becomeFirstResponder()
            examRLabel.textColor = UIColor.red
            return
        }
        
        examRLabel.textColor = UIColor.black
        
        //check especial
        if especialPicker.date < recursoPicker.date{
            especialPicker.becomeFirstResponder()
            exameELabel.textColor = UIColor.red
            return
        }
        
        examNLabel.textColor = UIColor.black
        
        //Check if the course doesnt exists if it does its now an update
        
        let courseFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Course")
        courseFetch.fetchLimit = 1
        courseFetch.predicate = NSPredicate(format: "name = %@", tfName.text!)
        courseFetch.returnsObjectsAsFaults = false
        var course = NSManagedObject()
        do{
            let fetchResult = try managedContext.fetch(courseFetch)
            let tmp = fetchResult.first as? NSManagedObject
            if fetchResult.first != nil{
                print("Curso ja existe vamos modificalo")
                nameLabel.textColor = UIColor.red
                tfName.becomeFirstResponder()
                course = tmp!
            }else{
                course = NSManagedObject(entity: courseEntity, insertInto: managedContext)
            }
            
        }catch{
            return
        }
        
        
        course.setValue(tfName.text, forKey: "name")
        course.setValue(yearData[yearPicker.selectedRow(inComponent:0)], forKey: "year")
        course.setValue(semesterData[yearPicker.selectedRow(inComponent:1)], forKey: "semester")
        course.setValue(normalPicker.date, forKey: "examN")
        course.setValue(recursoPicker.date, forKey: "examR")
        course.setValue(especialPicker.date, forKey: "examE")
        
        
        
        
        do {
            guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
            let managedContext = appDelegate.persistentContainer.viewContext
            try managedContext.save()
        } catch let error as NSError {
            print("Could not save. \(error), \(error.userInfo)")
        }
        print("--------------")
        
        navigationController?.popViewController(animated: true)
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1 {
            if component == 0 {
                return yearData.count
            }
                
            else {
                return semesterData.count
            }
        }
        return 0
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1 {
            if component == 0 {
                return String(yearData[row])
            } else {
                
                return String(semesterData[row])
            }
        }
        return ""
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

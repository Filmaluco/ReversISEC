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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        yearPicker.dataSource = self
        yearPicker.delegate = self
        yearPicker.tag = 1
    }
    
    @IBAction func onSave(_ sender: Any) {
        print("Esta a tentar gravar --------------")
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        let managedContext = appDelegate.persistentContainer.viewContext
        let courseEntity = NSEntityDescription.entity(forEntityName: "Course", in: managedContext)!
        
    
        //Check if the user doesnt exists
        let courseFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Course")
        courseFetch.fetchLimit = 1
        courseFetch.predicate = NSPredicate(format: "name = %@", tfName.text!)
        courseFetch.returnsObjectsAsFaults = false
        do{
            var fetchResult = try managedContext.fetch(courseFetch)
            if fetchResult.first != nil{
                print("Existe o curso #####")
                tfName.textColor = UIColor.red
                tfName.becomeFirstResponder()
                return
            }
        }catch{
            return
        }
        
        //Check if data is above today
        //check if recurso is above normal

        
        tfName.textColor = UIColor.black
        
        let course = NSManagedObject(entity: courseEntity, insertInto: managedContext)
        course.setValue(tfName.text, forKey: "name")
        
        
        course.setValue(yearData[yearPicker.selectedRow(inComponent:0)], forKey: "year")
        course.setValue(semesterData[yearPicker.selectedRow(inComponent:1)], forKey: "semester")
        
        
        course.setValue(normalPicker.date, forKey: "examN")
        
        
       
        course.setValue(recursoPicker.date, forKey: "examR")
        
        
        
        
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

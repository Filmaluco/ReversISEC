//
//  ExamTableViewController.swift
//  IsecExamManager
//
//  Created by Filipe Ramos Alves on 19/12/2018.
//  Copyright © 2018 Filipe Ramos Alves. All rights reserved.
//

import UIKit
import CoreData

class ExamTableViewController: UITableViewController {
    
    var courses  = [Course]() // Where Locations = your NSManaged Class
    var selectedCourse = Course()

    override func viewDidLoad() {
        super.viewDidLoad()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButton
        
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of 
        return courses.count
    }
    
    override func viewDidAppear(_ animated: Bool) {
        refreshData()
        tableView.reloadData()
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "itemCourse", for: indexPath)

        let row = indexPath.row
        
        cell.textLabel?.text = courses[row].name ?? "error loading from coursesData"
        //cell.detailTextLabel?.textColor = UIColor.red
        cell.detailTextLabel?.text = courses[row].name ?? "error loading from coursesData"
        
        
        // Configure the cell...

        return cell
    }
 

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */


    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            
            let course = courses[indexPath.row]
            print("Tentando apagar o user: \(course.name!)")
            
            //Remove query
            guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
            let context = appDelegate.persistentContainer.viewContext
            let fetchRequest: NSFetchRequest<Course> = Course.fetchRequest()
            fetchRequest.predicate = NSPredicate.init(format: "name = %@", course.name!)
            let objects = try! context.fetch(fetchRequest)
            for obj in objects {
                context.delete(obj)
            }
            
            do {
                try context.save() 
                    print("#########################")
            } catch {
                print("Failed to properly delete ###################")
            }
            
            courses.remove(at: indexPath.row)
            
            
            //TODO: remove from database 
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath)
        tableView.deselectRow(at: indexPath, animated: true)
        
        selectedCourse = courses[indexPath.row]
        performSegue(withIdentifier: "editExamSegue", sender: cell)
    }



    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "editExamSegue" {
            let vc = segue.destination as! AddViewController
            vc.oldCourse = selectedCourse
            /*
            let yearRow = Int(selectedCourse.year - 1)
            let semesterRow = Int(selectedCourse.semester - 1)
            vc.yearPicker?.selectRow(yearRow,inComponent: 0, animated: true)
            vc.yearPicker?.selectRow(semesterRow,inComponent: 0, animated: true)
            vc.tfName.text? = selectedCourse.name!
            vc.normalPicker?.date = selectedCourse.examN!
            vc.recursoPicker?.date = selectedCourse.examR!
            vc.especialPicker?.date = selectedCourse.examE!
            */
        }
    }
 
    func refreshData(){
        print("Refresquei os meus dados --------------")
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        let context = appDelegate.persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Course")
        do{
            try courses = context.fetch(fetchRequest) as! [Course]
        }catch _ as NSError{print("Falhou a carregar (blame apple)")}
        //para debug
        for course in courses {
            print(course.name ?? "notDefined")
            print(course.year )
            print(course.semester )
            print(course.examN ?? "notDefined")
            print(course.examR ?? "notDefined")
            print("--------------")
        }
    }

}

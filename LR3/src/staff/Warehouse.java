package staff;

import java.util.List;

public class Warehouse {
    /**
     * ManageWarehouse - функция, которая работает со списком работников
     * 
     * @param workers
     */
    public static void manageWarehouse(List<Worker> workers) {
        System.out.println("\n--- Warehouse Shift Started ---");
        for (var worker : workers) {
            worker.performRoutine();
        }
    }
}

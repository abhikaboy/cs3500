package src;

public class StockController {
  StockModel model;
  StockView view;

  

  public StockController(StockModel model, StockView view){
    this.model = model;
    this.view = view;
  }

  public void control(){
    // does jack shit for now
    this.view.printWelcome();
    while(true){
      
    }

  }
}

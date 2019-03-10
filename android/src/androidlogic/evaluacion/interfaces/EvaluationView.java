package androidlogic.evaluacion.interfaces;

import java.util.List;

import androidlogic.retrofitClasses.Questions;

public interface EvaluationView {

   void questionList(List<Questions> questionsList);
   void showPGB();
   void hidePGB();
   void showMessage(String string);
}

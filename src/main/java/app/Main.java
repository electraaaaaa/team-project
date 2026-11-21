package app;

import javax.swing.*;
public class Main {
    public static void main(String[] args){
        Appbuilder appbuilder = new Appbuilder();
        JFrame application = appbuilder
                .addProbabilityView()
                .addProbabilityUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}

package ma.sourireNetbis;

import ma.sourireNetbis.config.AppFactory;

/**
 * Hello world!
 *
 */
public class SourireNetbisApplication
{
    public static void main( String[] args )
    {

        //  AppFactory.getPatientController().showMainView();
       AppFactory.getUserController().showUserView();

    }
}

package test;

import controller.ControleurApplication;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		Plan plan = new Plan();
		ControleurApplication controleur = new ControleurApplication(plan, 1);
	}
}

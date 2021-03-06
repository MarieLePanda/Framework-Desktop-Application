/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfaces.IActionBackOffice;
import interfaces.IActionFrontOffice;
import panda.prod.application.PandaProdApplication;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import module.backoffice.ConnectAccountAction;
import module.backoffice.CreateAccountAction;
import module.factory.ActionBackOfficeFactory;
import module.factory.ActionFrontOffice;
import module.frontoffice.InscriptionFrameInitializer;
import view.InscriptionPPFrame;
import view.MainPPFrame;

public class Dispatcher implements ActionListener {

    private PandaProdApplication application;

    public Dispatcher() {
        application = PandaProdApplication.getApplication();
    }

    /**
     * Distribue les actions de l'utilsiateur à des traitements
     *
     * @param e Evénement décrivant l'action à réaliser
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionName = e.getActionCommand() + "Action";
        try {
            Method actionToPerform = Dispatcher.class.getDeclaredMethod(actionName);
            actionToPerform.invoke(this);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, "Unknown action: " + actionName, ex);
        }
    }

    public void logAccountAction() {
        System.err.println("log");
        IActionBackOffice action = ActionBackOfficeFactory.getInstance().createAction(ConnectAccountAction.class);
        boolean connect = action.execute();
        if (connect) {
            application.getMainFrame().dispose();
            application.setMainFrame(new MainPPFrame());
        }
    }

    public void updateAccountAction() {

    }

    public void forgottenPasswordAction() {

    }

    public void forgottenLoginAction() {

    }

    public void logoutAction() {

    }

    public void createAccountAction() { // compte cookie swipe a créé
        System.err.println("create");
        IActionBackOffice action = ActionBackOfficeFactory.getInstance().createAction(CreateAccountAction.class);
        boolean created = action.execute();
        if (created) {
            application.getFocusFrame().dispose();
        }
    }

    public void inscriptionAction() {
        System.err.println("Inscription");
        application.setFocusFrame(new InscriptionPPFrame());
        IActionFrontOffice action = ActionFrontOffice.getInstance().createAction(InscriptionFrameInitializer.class, application.getFocusFrame());
        action.execute();
    }
}
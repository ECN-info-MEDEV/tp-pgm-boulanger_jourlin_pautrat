/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.centralenantes.medev3;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * CLasse GUIAgrandissement permettant de mettre un facteur de grandissement et de l'appliquer à une image puis de la sauvegarder
 * @author Boulanger
 */
public class GUIAgrandissement extends JDialog{
    private PGM imageOld;
    private PGM imageNew;
    private String path;
    private JTextField agrandText;
    private JLabel imageLabel;
    /**
     * Constructeur de GUIAgrandissement
     * @param gui 
     */
    public GUIAgrandissement(GUIPgm gui){
        super(gui,"Agrandissement d'une image",true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,200);
        this.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(new GridLayout(1,2));
        
        //  GESTION DU SEUIL    \\
        JPanel paneSeuil= new JPanel();
        JLabel seuilLabel = new JLabel("Définir le facteur d'agrandissement");
        agrandText = new JTextField("insérer un nombre ici");
        
        JLabel txtImageLabel=new JLabel("L'image choisie est au chemin d'accès suivant :");
        imageLabel = new JLabel(path);
        
       
    
        paneSeuil.add(seuilLabel);
        paneSeuil.add(agrandText);
        paneSeuil.add(txtImageLabel);
        paneSeuil.add(imageLabel);    
        // GESTION DU CHARGEMENT DE L IMAGE \\
        
        JButton imageButton = new JButton("Selection de l'image");
        imageButton.addActionListener((e)->{
            try {
                ajoutImage();
            } catch (IOException ex) {
                Logger.getLogger(GUIAgrandissement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.addActionListener((e)->{
            try {
                Enregistrer();
            } catch (IOException ex) {
                Logger.getLogger(GUIAgrandissement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton appliquerAgrandissement = new JButton("Lancer l'agrandissement");
        appliquerAgrandissement.addActionListener((e)->Agrandissement());
        JPanel paneButton = new JPanel();
        paneButton.add(imageButton);
        paneButton.add(enregistrerButton);
        paneButton.add(appliquerAgrandissement);
        
        contentPane.add(paneSeuil);
        contentPane.add(paneButton);
        this.setVisible(true);
    }
     /**
     * Méthode permettant d'ajouter une image à l'utilisateur et de la stockée dans oldImage (appui bouton)
     */   
    private void ajoutImage() throws IOException{
        JFileChooser choix = new JFileChooser("Choisir une image");
        FileFilter imagesFilter = new FileNameExtensionFilter("Images","pgm");
        choix.setFileFilter(imagesFilter);
        int retour = choix.showOpenDialog(this);

        if(retour ==JFileChooser.APPROVE_OPTION){
            //Chargement de l'image
            path=choix.getSelectedFile().getAbsolutePath();
            imageLabel.setText(path);
            imageOld=PGM.lecture(choix.getSelectedFile().getAbsolutePath());
        }
    }
    
    /**
     * Méthode permettant à l'utilisateur de sauvegarder l'image résultante sous un nom qu'il choisi (appui bouton)
     */      
    private void Enregistrer() throws IOException{

        String inputValue = JOptionPane.showInputDialog("Indiquer le nom du fichier");
        PGM.enregistrement(imageNew,inputValue);
    }
    
    /**
     * Méthode qui appliquer l'agrandissement sur l'image et remplacer imageNew (appui bouton)
     */     
    private void Agrandissement(){
        imageNew=PGM.agrandissement(imageOld,Integer.parseInt(agrandText.getText()));
    }
}


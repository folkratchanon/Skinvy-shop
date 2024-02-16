package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Acne;
import com.hql.entity.Cleansing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CleansingController implements Initializable{
	@FXML
	private TableView <Cleansing>table;
	@FXML
	private TableColumn <Cleansing,String>col_id;
	@FXML
	private TableColumn <Cleansing,String>col_name;
	@FXML
	private TableColumn <Cleansing,String>col_brand;
	@FXML
	private TableColumn<Cleansing,Integer> col_price;
	@FXML
	private TextField text_id;
	@FXML
	private TextField text_name;
	@FXML
	private TextField text_brand;
	@FXML
	private TextField text_price;
	@FXML
	private TextField text_search;


	Main m = new Main();
	
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Cleansing>c = session.createQuery("from Cleansing").list();
			ObservableList<Cleansing>cList = FXCollections.observableArrayList(c);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("id_c"));
			col_name.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("name_c"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("brand_c"));
			col_price.setCellValueFactory(new PropertyValueFactory<Cleansing,Integer>("price_c"));
			
			table.setItems(cList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnAdd(ActionEvent event) {
		if(text_id.getText()!="" && text_name.getText()!="" && text_brand.getText()!="" && text_price.getText()!="") {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			try {
				Cleansing c = new Cleansing(text_id.getText(),text_name.getText(),text_brand.getText()
						,Integer.parseInt(text_price.getText()));
				
				//start a transaction
				session = factory.getCurrentSession();
				session.beginTransaction();
				
				session.save(c);
			
				//commit transaction
				session.getTransaction().commit();
			}finally {
				Alert a = new Alert(Alert.AlertType.NONE,"เพิ่มข้อมูลสำเร็จ",ButtonType.OK);
				a.show();
				initialize(null,null);
				factory.close();
			}
		}
	}

	@FXML
	public void btnEdit(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Cleansing c = session.get(Cleansing.class,text_id.getText());
			
			c.setId_c(text_id.getText());
			c.setName_c(text_name.getText());
			c.setBrand_c(text_brand.getText());
			c.setPrice_c(Integer.parseInt(text_price.getText()));
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			Alert a = new Alert(Alert.AlertType.NONE,"แก้ไขข้อมูลสำเร็จ",ButtonType.OK);
			a.show();
			initialize(null,null);
			factory.close();
		}
	}

	@FXML
	public void btnDelete(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Cleansing c = session.get(Cleansing.class,text_id.getText());
			//delete
			session.delete(c);
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			Alert a = new Alert(Alert.AlertType.NONE,"ลบข้อมูลสำเร็จ",ButtonType.OK);
			a.show();
			initialize(null,null);
			factory.close();
		}
	}

	@FXML
	public void btnSearch(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Cleansing>c = session.createQuery("from Cleansing").list();
			c = session.createQuery("from Cleansing c where c.id_c='"+text_search.getText()+
					"' OR c.name_c='"+text_search.getText()+
					"' OR c.brand_c='"+text_search.getText()+
					"' OR c.price_c='"+text_search.getText()+"'").list();
			
			ObservableList<Cleansing>cList = FXCollections.observableArrayList(c);
			table.setItems(FXCollections.observableArrayList(cList));
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	@FXML
	public void refresh(MouseEvent event) {
		text_id.clear();
		text_name.clear();
		text_brand.clear();
		text_price.clear();
		text_search.clear();
		initialize(null,null);
	}
	@FXML
	public void rowClick(MouseEvent event) {
		Cleansing click = table.getSelectionModel().getSelectedItem();
		text_id.setText(String.valueOf(click.getId_c()));
		text_name.setText(String.valueOf(click.getName_c()));
		text_brand.setText(String.valueOf(click.getBrand_c()));
		text_price.setText(String.valueOf(click.getPrice_c()));
	}

	@FXML
	public void btnAcne(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Acne.fxml");
	}
	@FXML
	public void btnSunscreen(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Sunscreen.fxml");
	}
	@FXML
	public void btnMember(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Member.fxml");
	}
}

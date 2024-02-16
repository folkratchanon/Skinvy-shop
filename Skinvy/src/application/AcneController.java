package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Acne;

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

public class AcneController implements Initializable{
	@FXML
	private TableView <Acne>table;
	@FXML
	private TableColumn <Acne,String>col_id;
	@FXML
	private TableColumn <Acne,String>col_name;
	@FXML
	private TableColumn<Acne,String> col_brand;
	@FXML
	private TableColumn <Acne,Integer>col_price;
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
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Acne>a = session.createQuery("from Acne").list();
			ObservableList<Acne>aList = FXCollections.observableArrayList(a);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Acne,String>("id_a"));
			col_name.setCellValueFactory(new PropertyValueFactory<Acne,String>("name_a"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Acne,String>("brand_a"));
			col_price.setCellValueFactory(new PropertyValueFactory<Acne,Integer>("price_a"));
			
			table.setItems(aList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void btnAdd(ActionEvent event) {
		if(text_id.getText()!="" && text_name.getText()!="" && text_brand.getText()!="" && text_price.getText()!="") {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			try {
				Acne a = new Acne(text_id.getText(),text_name.getText(),text_brand.getText()
						,Integer.parseInt(text_price.getText()));
				
				//start a transaction
				session = factory.getCurrentSession();
				session.beginTransaction();
				
				session.save(a);
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Acne a = session.get(Acne.class,text_id.getText());
			
			a.setId_a(text_id.getText());
			a.setName_a(text_name.getText());
			a.setBrand_a(text_brand.getText());
			a.setPrice_a(Integer.parseInt(text_price.getText()));
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Acne a = session.get(Acne.class,text_id.getText());
			//delete
			session.delete(a);
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Acne>a = session.createQuery("from Acne").list();
			a = session.createQuery("from Acne c where c.id_a='"+text_search.getText()+
					"' OR c.name_a='"+text_search.getText()+
					"' OR c.brand_a='"+text_search.getText()+
					"' OR c.price_a='"+text_search.getText()+"'").list();
			
			ObservableList<Acne>aList = FXCollections.observableArrayList(a);
			table.setItems(FXCollections.observableArrayList(aList));
			
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
		Acne click = table.getSelectionModel().getSelectedItem();
		text_id.setText(String.valueOf(click.getId_a()));
		text_name.setText(String.valueOf(click.getName_a()));
		text_brand.setText(String.valueOf(click.getBrand_a()));
		text_price.setText(String.valueOf(click.getPrice_a()));
	}
	
	@FXML
	public void btnMember(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Member.fxml");
	}
	@FXML
	public void btnCleansing(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Cleansing.fxml");
	}
	@FXML
	public void btnSunscreen(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Sunscreen.fxml");
	}
}

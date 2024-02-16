package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Cleansing;
import com.hql.entity.Sunscreen;

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

public class SunscreenController implements Initializable{
	@FXML
	private TableView<Sunscreen> table;
	@FXML
	private TableColumn <Sunscreen,String>col_id;
	@FXML
	private TableColumn<Sunscreen,String> col_name;
	@FXML
	private TableColumn <Sunscreen,String>col_brand;
	@FXML
	private TableColumn<Sunscreen,Integer>col_price;
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
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Sunscreen>s = session.createQuery("from Sunscreen").list();
			ObservableList<Sunscreen>sList = FXCollections.observableArrayList(s);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("id_s"));
			col_name.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("name_s"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("brand_s"));
			col_price.setCellValueFactory(new PropertyValueFactory<Sunscreen,Integer>("price_s"));
			
			table.setItems(sList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void btnAdd(ActionEvent event) {
		if(text_id.getText()!="" && text_name.getText()!="" && text_brand.getText()!="" && text_price.getText()!="") {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			try {
				Sunscreen s = new Sunscreen(text_id.getText(),text_name.getText(),text_brand.getText()
						,Integer.parseInt(text_price.getText()));
				
				//start a transaction
				session = factory.getCurrentSession();
				session.beginTransaction();
				
				session.save(s);
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Sunscreen s = session.get(Sunscreen.class,text_id.getText());
			
			s.setId_s(text_id.getText());
			s.setName_s(text_name.getText());
			s.setBrand_s(text_brand.getText());
			s.setPrice_s(Integer.parseInt(text_price.getText()));
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Sunscreen s = session.get(Sunscreen.class,text_id.getText());
			//delete
			session.delete(s);
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Sunscreen>s = session.createQuery("from Sunscreen").list();
			s= session.createQuery("from Sunscreen c where c.id_s='"+text_search.getText()+
					"' OR c.name_s='"+text_search.getText()+
					"' OR c.brand_s='"+text_search.getText()+
					"' OR c.price_s='"+text_search.getText()+"'").list();
			
			ObservableList<Sunscreen>sList = FXCollections.observableArrayList(s);
			table.setItems(FXCollections.observableArrayList(sList));
			
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
		Sunscreen click = table.getSelectionModel().getSelectedItem();
		text_id.setText(String.valueOf(click.getId_s()));
		text_name.setText(String.valueOf(click.getName_s()));
		text_brand.setText(String.valueOf(click.getBrand_s()));
		text_price.setText(String.valueOf(click.getPrice_s()));
	}
	@FXML
	public void btnAcne(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Acne.fxml");
	}

	@FXML
	public void btnCleansing(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Cleansing.fxml");
	}

	@FXML
	public void btnMember(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Member.fxml");
	}
}

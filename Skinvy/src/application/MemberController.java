package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Member;
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

public class MemberController implements Initializable{
	@FXML
	private TableView<Member> table;
	@FXML
	private TableColumn <Member,Integer>col_id;
	@FXML
	private TableColumn<Member,String> col_fname;
	@FXML
	private TableColumn <Member,String>col_lname;
	@FXML
	private TableColumn <Member,String>col_phone;
	@FXML
	private TableColumn <Member,String>col_email;
	@FXML
	private TextField text_id;
	@FXML
	private TextField text_fname;
	@FXML
	private TextField text_phone;
	@FXML
	private TextField text_email;
	@FXML
	private TextField text_search;
	@FXML
	private TextField text_lname;
	
Main m = new Main();
	
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Member>mb = session.createQuery("from Member").list();
			ObservableList<Member>mbList = FXCollections.observableArrayList(mb);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Member,Integer>("id_m"));
			col_fname.setCellValueFactory(new PropertyValueFactory<Member,String>("fname_m"));
			col_lname.setCellValueFactory(new PropertyValueFactory<Member,String>("lname_m"));
			col_phone.setCellValueFactory(new PropertyValueFactory<Member,String>("phone_m"));
			col_email.setCellValueFactory(new PropertyValueFactory<Member,String>("email_m"));

			table.setItems(mbList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void btnEdit(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Member mb = session.get(Member.class,text_id.getText());
			
			mb.setFname_m(text_fname.getText());
			mb.setLname_m(text_lname.getText());
			mb.setPhone_m(text_phone.getText());
			mb.setEmail_m(text_email.getText());
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Member mb = session.get(Member.class,text_id.getText());
			//delete
			session.delete(mb);
			
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Member>mb = session.createQuery("from Member").list();
			mb = session.createQuery("from Member c where c.id_m='"+text_search.getText()+
					"' OR c.name_m='"+text_search.getText()+
					"' OR c.brand_m='"+text_search.getText()+
					"' OR c.price_m='"+text_search.getText()+"'").list();
			
			ObservableList<Member>mbList = FXCollections.observableArrayList(mb);
			table.setItems(FXCollections.observableArrayList(mbList));
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	@FXML
	public void refresh(MouseEvent event) {
		text_id.clear();
		text_fname.clear();
		text_lname.clear();
		text_phone.clear();
		text_email.clear();
		text_search.clear();
		initialize(null,null);
	}

	@FXML
	public void rowClick(MouseEvent event) {
		Member click = table.getSelectionModel().getSelectedItem();
		text_id.setText(String.valueOf(click.getId_m()));
		text_fname.setText(String.valueOf(click.getFname_m()));
		text_lname.setText(String.valueOf(click.getLname_m()));
		text_phone.setText(String.valueOf(click.getPhone_m()));
		text_email.setText(String.valueOf(click.getEmail_m()));
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
	public void btnSunscreen(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Sunscreen.fxml");
	}
}

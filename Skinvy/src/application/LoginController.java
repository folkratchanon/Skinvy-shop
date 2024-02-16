package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Member;
import com.hql.entity.Cart;

import javafx.event.ActionEvent;

public class LoginController {
	@FXML
	private TextField text_email;
	@FXML
	private TextField text_password;

	Main m = new Main();
	@FXML
	public void btnLogin(ActionEvent event) throws IOException{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		session = factory.getCurrentSession();
		session.beginTransaction();
		
		List<Member>emptylist = Collections.emptyList();  
		List<Member>user = session.createQuery("from Member").list();
		user = session.createQuery("from Member u where u.email_m='"+text_email.getText()+"' AND u.password_m='"+text_password.getText()+"'").list();
		
		if((text_email.getText()).equals("admin@skinvy.com")&&(text_password.getText()).equals("admin")) {
			m.changeScene("/fxml/Acne.fxml");
			Alert a = new Alert(Alert.AlertType.NONE,"เข้าสู่ระบบ Admin สำเร็จ!",ButtonType.OK);
			a.show();
		}
		else if(user.equals(emptylist)) {
			Alert a = new Alert(Alert.AlertType.NONE,"รหัสผ่านไม่ถูกต้อง",ButtonType.OK);
			a.show();
			System.out.println("Failed");
		}else {
			Alert a = new Alert(Alert.AlertType.NONE,"เข้าสู่ระบบสำเร็จ",ButtonType.OK);
			a.show();
			clearCart();
			System.out.println("Success");
			m.changeScene("/fxml/ShopA.fxml");
		}
		//commit transaction
		session.getTransaction().commit();
		factory.close();
	}

	@FXML
	public void btnSignup(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Register.fxml");
	}
	
	public void clearCart() throws IOException{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cart.class).buildSessionFactory();
		Session session = factory.getCurrentSession();	
		//start a transaction
		session = factory.getCurrentSession();
		session.beginTransaction();
		
		//Clear all database user_login
		session.createSQLQuery("truncate table cart").executeUpdate();
		
		//commit transaction
		session.getTransaction().commit();
		factory.close();
	}
}

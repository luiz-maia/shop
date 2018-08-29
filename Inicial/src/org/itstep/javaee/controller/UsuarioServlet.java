package org.itstep.javaee.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListSelectionEvent;

import org.itstep.javaee.dao.UsuarioDao;
import org.itstep.javaee.dao.exception.DaoException;
import org.itstep.javaee.dao.impl.UsuarioDaoImpl;
import org.itstep.javaee.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuarioDao usuarioDao = new UsuarioDaoImpl();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			if (request.getParameter("operacao") != null) {
				Usuario usuario = new Usuario();
				String operacao = request.getParameter("operacao");
				if (operacao.equals("insert")) {
					String nome = (String) request.getParameter("nome");
					String cpf = (String) request.getParameter("cpf");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date validade = new Date(sdf.parse(request.getParameter("validade")).getTime());
					usuario.setNome(nome);
					usuario.setCpf(cpf);
					usuario.setValidade(validade);
					usuarioDao.create(usuario);
				} else if (operacao.equals("delete")) {
					String cpf = (String) request.getParameter("cpf");
					usuario.setCpf(cpf);
					usuarioDao.delete(usuario);
				}
			}
			usuarios = usuarioDao.findByAll();
			System.out.println(Arrays.toString(usuarios.toArray()));
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

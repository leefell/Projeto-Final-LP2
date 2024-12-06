package br.com.projetofinal.VIEW;

import br.com.projetofinal.CTR.TaskCTR;
import br.com.projetofinal.DAO.ConnectionDAO;
import br.com.projetofinal.DTO.ConcreteTaskFactoryDTO;
import br.com.projetofinal.DTO.NormalTaskDTO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class TaskVIEW extends JFrame {
    private JTextField titleTask;
    private Connection con = ConnectionDAO.con;
    private TaskCTR controller = new TaskCTR(con);

    public TaskVIEW() {
        initComponents();
        setSize(1267, 788); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        titleTask = new JTextField();
        titleTask.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblAFazer = new JLabel("A Fazer");
        lblAFazer.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblFazendo = new JLabel("Fazendo");
        lblFazendo.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblFeito = new JLabel("Feito");
        lblFeito.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JTable tableA = new JTable();
        tableA.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null},
        	},
        	new String[] {
        		"ID", "Tarefa", "Status", "Data"
        	}
        ) {
        	Class[] columnTypes = new Class[] {
        		Integer.class, String.class, Object.class, String.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        });
        tableA.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableA.getColumnModel().getColumn(0).setMaxWidth(50);
        tableA.getColumnModel().getColumn(2).setMaxWidth(100);
        tableA.getColumnModel().getColumn(3).setMaxWidth(100);

        JTable tableB = new JTable();
        tableB.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tarefas em Progresso" }));

        JTable tableC = new JTable();
        tableC.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tarefas Feitas" }));

        JScrollPane scrollPaneA = new JScrollPane(tableA);
        JScrollPane scrollPaneB = new JScrollPane(tableB);
        JScrollPane scrollPaneC = new JScrollPane(tableC);
        
        JButton btnFazerParaFazendo = new JButton("->");
        btnFazerParaFazendo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowSelected = tableA.getSelectedRow();
        		
        		if (rowSelected != -1) {
        			int id = (int) tableA.getValueAt(rowSelected, 0);
        			controller.updateTaskTodoToDoing(id);
        		}
        		
        	}
        });
        
        btnFazerParaFazendo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JButton btnFazendoParaFazer = new JButton("<-");
        btnFazendoParaFazer.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JButton btnFazerParaFazendo_1 = new JButton("->");
        btnFazerParaFazendo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JButton btnFazendoParaFazer_1 = new JButton("<-");
        btnFazendoParaFazer_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JComboBox typeTask = new JComboBox();
        typeTask.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Urgent"}));

        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        btnOk.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String title = titleTask.getText();
        		String type = (String) typeTask.getSelectedItem();
        		
        		ConcreteTaskFactoryDTO concreteTaskFactoryDTO = new ConcreteTaskFactoryDTO();
        		controller.createTask(concreteTaskFactoryDTO.createTask(title, type));
        	}
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(btnDeletar)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnEditar)
        					.addGap(30)
        					.addComponent(titleTask, GroupLayout.PREFERRED_SIZE, 1041, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(typeTask, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnOk))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(lblAFazer)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(btnFazerParaFazendo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
        						.addComponent(scrollPaneA, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
        					.addGap(10)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(btnFazendoParaFazer, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblFazendo)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnFazerParaFazendo_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
        						.addComponent(scrollPaneB, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
        					.addGap(10)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(scrollPaneC, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(btnFazendoParaFazer_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblFeito)))))
        			.addContainerGap(166, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnDeletar)
        				.addComponent(btnEditar)
        				.addComponent(titleTask, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnOk)
        				.addComponent(typeTask, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(10)
        					.addComponent(lblAFazer))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(btnFazerParaFazendo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(btnFazendoParaFazer, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        							.addComponent(lblFazendo))))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnFazerParaFazendo_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(btnFazendoParaFazer_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblFeito))))
        			.addGap(15)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPaneA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(scrollPaneB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(scrollPaneC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(10))
        );
        getContentPane().setLayout(layout);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskVIEW view = new TaskVIEW();
            view.setVisible(true);
        });
    }
}
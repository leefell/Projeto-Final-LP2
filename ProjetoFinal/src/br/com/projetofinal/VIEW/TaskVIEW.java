package br.com.projetofinal.VIEW;

import br.com.projetofinal.CTR.TaskCTR;
import br.com.projetofinal.DAO.ConnectionDAO;
import br.com.projetofinal.DTO.ConcreteTaskFactoryDTO;
import br.com.projetofinal.DTO.ITaskDTO;
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
    private TaskCTR controller = new TaskCTR();
    
    private JTable tableA;
    private JTable tableB;
    private JTable tableC;

    public TaskVIEW() {
        initComponents();
        setSize(1267, 788); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateTables();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        titleTask = new JTextField();
        titleTask.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Verificar se alguma tarefa está selecionada nas tabelas
                int rowSelected = tableA.getSelectedRow();
                if (rowSelected == -1) {
                    rowSelected = tableB.getSelectedRow();
                }
                if (rowSelected == -1) {
                    rowSelected = tableC.getSelectedRow();
                }

                if (rowSelected != -1) {
                    // Obter a ID da tarefa selecionada (a ID pode ser obtida de qualquer tabela, dependendo da seleção)
                    int id = (int) tableA.getValueAt(rowSelected, 0);

                    // Exibir a caixa de diálogo de confirmação
                    int option = JOptionPane.showConfirmDialog(
                        null, 
                        "Tem certeza de que deseja excluir esta tarefa?", 
                        "Confirmar Exclusão", 
                        JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        // Chamar o controlador para excluir a tarefa
                        controller.deleteTask(id);
                        
                        // Atualizar as tabelas após a exclusão
                        updateTables();
                        JOptionPane.showMessageDialog(null, "Tarefa excluída com sucesso!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma tarefa para excluir.");
                }
            }
        });


        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int rowSelected = -1;

                if (tableA.getSelectedRow() != -1) {
                    rowSelected = tableA.getSelectedRow();
                } else if (tableB.getSelectedRow() != -1) {
                    rowSelected = tableB.getSelectedRow();
                } else if (tableC.getSelectedRow() != -1) {
                    rowSelected = tableC.getSelectedRow();
                }

                if (rowSelected != -1) {
                    // Pega a tarefa q esta selecionada
                    int id = (int) tableA.getValueAt(rowSelected, 0);
                    String title = (String) tableA.getValueAt(rowSelected, 1);
                    String type = (String) tableA.getValueAt(rowSelected, 2);

                    // Cria os componentes que vao estar na janela
                    JTextField titleField = new JTextField(title);
                    JComboBox<String> typeComboBox = new JComboBox<>(new String[] {"Normal", "Urgent"});
                    typeComboBox.setSelectedItem(type);

                    // Texto e tipo das opções da janela
                    Object[] message = {
                        "Título:", titleField,
                        "Tipo:", typeComboBox
                    };

                    // Add opções da janela
                    int option = JOptionPane.showConfirmDialog(null, message, "Editar Tarefa", JOptionPane.OK_CANCEL_OPTION);
                    
                    // Ação para quando clicar no OK
                    if (option == JOptionPane.OK_OPTION) {
                        String newTitle = titleField.getText();
                        String newType = (String) typeComboBox.getSelectedItem();

                        if (newTitle.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O título não pode estar vazio.");
                            return;
                        }

                        if (newType == null || newType.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Por favor, selecione o tipo da tarefa.");
                            return;
                        }


                        controller.updateTask(id, newTitle, newType);
                        updateTables();
                        JOptionPane.showMessageDialog(null, "Tarefa atualizada com sucesso!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma tarefa para editar.");
                }
            }
        });


        JLabel lblAFazer = new JLabel("A Fazer");
        lblAFazer.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblFazendo = new JLabel("Fazendo");
        lblFazendo.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblFeito = new JLabel("Feito");
        lblFeito.setFont(new Font("Tahoma", Font.PLAIN, 14));

        tableA = new JTable();
        tableA.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"ID", "Tarefas a Fazer", "Tipo" }));

        tableB = new JTable();
        tableB.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"ID", "Tarefas em Progresso", "Tipo" }));

        tableC = new JTable();
        tableC.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"ID", "Tarefas Feitas", "Tipo" }));

        JScrollPane scrollPaneA = new JScrollPane(tableA);
        JScrollPane scrollPaneB = new JScrollPane(tableB);
        JScrollPane scrollPaneC = new JScrollPane(tableC);
        
        JButton btnFazerParaFazendo = new JButton("->");
        btnFazerParaFazendo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowSelected = tableA.getSelectedRow();
        		
        		if (rowSelected != -1) {
        			int id = (int) tableA.getValueAt(rowSelected, 0);
        			String newStatus = "DOING"; 
        			controller.updateTaskStatus(id, newStatus);
        			updateTables();
        		}
        		
        	}
        });
        
        btnFazerParaFazendo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JButton btnFazendoParaFazer = new JButton("<-");
        btnFazendoParaFazer.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFazendoParaFazer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowSelected = tableB.getSelectedRow();
        		
        		if (rowSelected != -1) {
        			int id = (int) tableB.getValueAt(rowSelected, 0);
        			String newStatus = "TODO"; 
        			controller.updateTaskStatus(id, newStatus);
        			updateTables();
        		}
        		
        	}
        });
        
        JButton btnFazerParaFeita = new JButton("->");
        btnFazerParaFeita.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFazerParaFeita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowSelected = tableB.getSelectedRow();
        		
        		if (rowSelected != -1) {
        			int id = (int) tableB.getValueAt(rowSelected, 0);
        			String newStatus = "DONE"; 
        			controller.updateTaskStatus(id, newStatus);
        			updateTables();
        		}
        		
        	}
        });
        
        JButton btnFeitaParaFazendo = new JButton("<-");
        btnFeitaParaFazendo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFeitaParaFazendo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowSelected = tableC.getSelectedRow();
        		
        		if (rowSelected != -1) {
        			int id = (int) tableC.getValueAt(rowSelected, 0);
        			String newStatus = "DOING"; 
        			controller.updateTaskStatus(id, newStatus);
        			updateTables();
        		}
        		
        	}
        });
        
        JComboBox typeTask = new JComboBox();
        typeTask.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Urgent"}));

        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleTask.getText();
                String type = (String) typeTask.getSelectedItem();

                if (title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O título da tarefa não pode estar vazio.");
                    return;
                }

                if (type == null || type.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione o tipo da tarefa.");
                    return;
                }

                ConcreteTaskFactoryDTO factory = new ConcreteTaskFactoryDTO();
                controller.createTask(factory.createTask(title, type));
                JOptionPane.showMessageDialog(null, "Tarefa criada com sucesso!");
                titleTask.setText("");
                updateTables();
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
        							.addComponent(btnFazerParaFeita, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
        						.addComponent(scrollPaneB, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
        					.addGap(10)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(scrollPaneC, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(btnFeitaParaFazendo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
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
        					.addComponent(btnFazerParaFeita, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(btnFeitaParaFazendo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
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
    
    private void updateTables() {
        DefaultTableModel modelA = (DefaultTableModel) tableA.getModel();
        DefaultTableModel modelB = (DefaultTableModel) tableB.getModel();
        DefaultTableModel modelC = (DefaultTableModel) tableC.getModel();

        // Limpar as tabelas
        modelA.setRowCount(0);
        modelB.setRowCount(0);
        modelC.setRowCount(0);

        // Buscar dados do controlador
        System.out.println("Tarefas retornadas: " + controller.getAllTasks().size());
        var tasks = controller.getAllTasks();
        for (ITaskDTO task : tasks) {
        	System.out.println("Passou aqui uma vez " + task.getType() + " status: " + task.getStatus());
            // Adicione as tarefas nas tabelas apropriadas
            switch (task.getStatus()) {
                case "TODO":
                	System.out.println("Adicionou na tabela A");
                    modelA.addRow(new Object[] {task.getId(), task.getTitle(), task.getType() });
                    break;
                case "DOING":
                	System.out.println("Adicionou na tabela B");
                    modelB.addRow(new Object[] {task.getId(), task.getTitle(), task.getType() });
                    break;
                case "DONE":
                	System.out.println("Adicionou na tabela C");
                    modelC.addRow(new Object[] {task.getId(), task.getTitle(), task.getType() });
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskVIEW view = new TaskVIEW();
            view.setVisible(true);
        });
    }
}
package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {
   private JTextField resultTxt;
   private ArrayList<String> calculation = new ArrayList<String>(); 
   private String num="";
   private String prevOperation="";
   // Collection�� ���̰� ������ ���� �ʴ�   
   public Calculator() {
      
      Container contentPane = this.getContentPane(); // ctrl + shift + o
      contentPane.setBackground(Color.GRAY);//(new Color(r[256],g[256],b[256]));
      
      resultTxt = new JTextField();
      resultTxt.setBackground(Color.WHITE);
      resultTxt.setFont(new Font("���� ���",Font.BOLD,40));
      resultTxt.setHorizontalAlignment(JTextField.RIGHT);
      resultTxt.setEditable(false); // �� �����ȵǰ� ����....
      //resultTxt.setSize(400,200);
      resultTxt.setPreferredSize(new Dimension(400,80));//
      
      JPanel btnPanel = new JPanel();// JButton�� ���� container ���� 
      btnPanel.setLayout(new GridLayout(4,4,2,2)); // 4�ٿ� 4ĭ 2,2
      
      contentPane.add(resultTxt,BorderLayout.NORTH);
      contentPane.add(btnPanel,BorderLayout.SOUTH);
      
      // ��ư���� �ѹ��� �����ϱ� ���� �迭�� �ϳ� ����...
      JButton btns[] = new JButton[16];
      String numberAndOperator[] = {"C","��","��","=","7","8","9","+","4","5","6","-","1","2","3","0"};
      for(int i=0;i<16;i++) {
         btns[i] = new JButton(numberAndOperator[i]);
         btns[i].setFont(new Font("���� ���",Font.BOLD,24));
         btns[i].setForeground(Color.WHITE);
         btns[i].setBorderPainted(false);
         btns[i].setPreferredSize(new Dimension(80,80));
         btns[i].addActionListener(new BtnActionListener());
         btnPanel.add(btns[i]);
         if(i==0) {
            btns[i].setBackground(Color.RED);
         } else if(i==1 || i==2 || i==3 || i==7 || i==11) {
            btns[i].setBackground(Color.GRAY);
         } else {
            btns[i].setBackground(Color.BLACK);
         }
      }
      
      this.setTitle("����");
      this.setSize(400,500);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationRelativeTo(null);
      this.pack();  // �ڽĵ��� ũ�⿡ ���缭 ���̰ų� �ø���... (�����߱�)
      this.setVisible(true);
   }
   class BtnActionListener implements ActionListener  {
      @Override
      public void actionPerformed(ActionEvent e) {
         JButton clickedBtn = (JButton)e.getSource();// �̺�Ʈ�� �߻��� ������Ʈ
         String operation = e.getActionCommand();
         
         if(operation.equals("C")) {
            num="";
            resultTxt.setText("");
         } else if(operation.equals("=")) {
            //���⼭ ��� ��� ��ȯ
            //���ڸ� �ɰ��� �������� ����������....
            String result = Double.toString( calcurate(resultTxt.getText()) );
            resultTxt.setText(result);
         } else if( operation.equals("+") || operation.equals("-") || operation.equals("��") || operation.equals("��") ) {
            // �ƹ��͵� ������ ���� ������.....
            if( resultTxt.getText().equals("") && operation.equals("-") ) {
               resultTxt.setText(resultTxt.getText()+operation);
            } else if (!resultTxt.getText().equals("") && 
                     !prevOperation.equals("+") && 
                     !prevOperation.equals("-") && 
                     !prevOperation.equals("��") &&
                     !prevOperation.equals("��")
                     ) {
               resultTxt.setText(resultTxt.getText()+operation);
            } 
         } else {
            resultTxt.setText( resultTxt.getText()+operation );
         }
         //String operation = clickedBtn.getText().trim();
         prevOperation = operation;
      }
   }
   //�����ڴ� �ѹ��� ������ ó�� �ѹ�
   
   // �Ű������� ���޹��� ���ڸ� ���ڿ� �����ڷ� �и��ؼ� calculation�� �о�ֱ�...
   private void resultTxtParsing(String tempTxt) {
      calculation.clear();
      for(int i = 0;i<tempTxt.length();i++) {
         char ch = tempTxt.charAt(i);
         if(ch=='��' || ch=='��' || ch=='+' || ch=='-') {
            // �������϶�
            calculation.add(num);
            num="";
            calculation.add(ch+"");
         } else {
            num+=ch;
         }
      }
      calculation.add(num);
      calculation.remove("");
      num="";
      System.out.println(calculation.toString());
   }
   
   private double calcurate(String tempTxt) {
      //�ݺ��� ������...
      String mode = "";
      double prevNum = 0;
      double currentNum = 0;
      resultTxtParsing(resultTxt.getText());
      //������ ���ٰ� +,-,��,��
      //10+3+3+10+5  (10,+,3,+,3,+10,+,5)
      for(String str : calculation ) {
         if(str.equals("+")) {
            mode = "plus";
         } else if(str.equals("-")) {
            mode = "minus";
         } else if(str.equals("��")) {
            mode = "multiply";
         } else if(str.equals("��")) {
            mode = "divide";
         } else {
            //�����ڰ� �ƴ϶��....���ڶ��
            currentNum = Double.parseDouble(str);   //���ڿ��� double�� �ٲ۴�.
            if(mode.equals("plus")) {
               prevNum+=currentNum;
            } else if(mode.equals("minus")) {
               prevNum-=currentNum;
            } else if(mode.equals("multiply")) {
               prevNum*=currentNum;
            } else if(mode.equals("divide")) {
               prevNum/=currentNum;
            } else {
               prevNum = currentNum;
            }
         } 
      }
      System.out.println(prevNum);
      return prevNum;
   }
   
   //resultTxtParsing("10+10+10");
   
   public static void main(String[] args) {
      new Calculator();
   }
}










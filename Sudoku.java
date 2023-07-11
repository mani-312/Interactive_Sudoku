import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Line2D;
import javax.swing.text.*;
import java.util.Random;
public class Sudoku extends JFrame implements KeyListener
{
	static JTextField t[][];
	static int count=0;
	JLabel l;
	static int a[][];
	Sudoku()
	{
		int i,j,x=100,y=200;
		t=new JTextField[9][9];
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
		);
		Font font=new Font("SanSerif",Font.PLAIN,50);
		//Button b=new Button("Hello");
		//b.setBounds(150,10,150,150);
		//add(b);
		for(i=0;i<9;i++)
		{
			x=100;
			for(j=0;j<9;j++)
			{	
				t[i][j]=new JTextField(1);
				t[i][j].setBounds(x,y,60,60);
				t[i][j].setHorizontalAlignment(JTextField.CENTER);
				t[i][j].setFont(font);
				t[i][j].setDocument(new Limit(1));
				t[i][j].addKeyListener(this);
				add(t[i][j]);
				if(a[i][j]!=0)
				{
					count++;
					t[i][j].setEditable(false);
					t[i][j].setText(String.valueOf(a[i][j]));
			    }
				x+=60;
			}
			y+=60;
		}
		l=new JLabel();
		l.setBounds(50,10,100,200);
		add(l);
		


		// Container cp = getContentPane();
        // cp.add(new JComponent() {
        //     public void paintComponent(Graphics g) {
        //         Graphics2D g2 = (Graphics2D) g;
        //         g2.setStroke(new BasicStroke(10));
        //         g2.draw(new Line2D.Float(200, 300, 400, 500));
        //     }
        // });
        setLayout(null);
		setSize(1000,1000);
        setVisible(true);
	}
	/*public void paint(Graphics g)
	{
		g.drawLine(10,10,100,10);
	}*/
	public void keyReleased(KeyEvent e)
	{
		int i=0,j=0;
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
			{
				if(t[i][j]==e.getSource())
				{
					char ch=t[i][j].getText().charAt(0);
					if(!Character.isDigit(ch)||ch=='0')
					{
						t[i][j].setText("");
						continue;
					}
					int v=Integer.parseInt(t[i][j].getText());
					if(check(a,i,j,v)==0)
					{
						l.setText("Not a correct choose");
						t[i][j].setText("");
						if(a[i][j]!=0)
							count--;
						a[i][j]=0;
					}
					else
					{
						if(a[i][j]==0)
							count++;	
						l.setText("Thumbs up! "+count);
						a[i][j]=v;
						if(count==81)
							l.setText("You Rocked it");
						
					}
				}
			}
		}
	}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public static void main(String args[])
	{
		a=new int[9][9];
		init(a);
		Random rand=new Random();
		int r,c,v,i;
		for(i=0;i<40;i++)
		{
			r=rand.nextInt(8);
			c=rand.nextInt(8);
			v=rand.nextInt(8)+1;
			if(a[r][c]==0&&check(a,r,c,v)==1)
			{
				int temp_a[][]=new int[9][9];
				init(temp_a);
				copy(temp_a,a);
				temp_a[r][c]=v;
				int t=ways(temp_a,0,0,0);
				if(t==2)
                    a[r][c]=v;
                else if(t==1)
                { 
                    a[r][c]=v;
					System.out.printf("Number of solutions = %d",sudoku(a,0,0,0));
                    new Sudoku();
                    break;
                }
                else
                    i--;
			}
			else
			{
				i--;
			}
		}
		
	}
	static int ways(int a[][],int r,int c,int count)
    {
		if(r==9)
		{
			//print(a);
			count++;
			return count;
		}
		else if(r!=9&&c==9)
		{
			count=ways(a,r+1,0,count);
			return count;
		}
		else
		{
			if(a[r][c]!=0)
			{
				count=ways(a,r,c+1,count);
				return count;
			}
			for(int v=1;v<=9;v++)
			{
				if(check(a,r,c,v)==1)
				{
					a[r][c]=v;
					count=ways(a,r,c+1,count);
					if(count==2)
						return count;
					a[r][c]=0;
				}
			}
			return count;
		}
    }
	static int sudoku(int a[][],int r,int c,int count)
    {
		if(r==9)
		{
			print(a);
			count++;
			return count;
		}
		else if(r!=9&&c==9)
		{
			count=sudoku(a,r+1,0,count);
			return count;
		}
		else
		{
			if(a[r][c]!=0)
			{
				count=sudoku(a,r,c+1,count);
				return count;
			}
			for(int v=1;v<=9;v++)
			{
				if(check(a,r,c,v)==1)
				{
					a[r][c]=v;
					count=sudoku(a,r,c+1,count);
					//if(count==2)
						//return count;
					a[r][c]=0;
				}
			}
			return count;
		}
    }
	static int check(int a[][],int row,int col,int value)
    {
		int i,j;
		for(i=0;i<9;i++)
		{
			if(a[row][i]==value)
				return 0;
		}
		for(j=0;j<9;j++)
		{
			if(a[j][col]==value)
				return 0;
		}
		int grid_row=row-row%3,grid_col=col-col%3;
		for(i=grid_row;i<grid_row+3;i++)
		{
			for(j=grid_col;j<grid_col+3;j++)
			{
				if(a[i][j]==value)
					return 0;
			}

		}
		return 1;
	}
	
	static void init(int a[][])
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
				a[i][j]=0;
		}
	}
    static void copy(int a[][],int b[][])
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
				a[i][j]=b[i][j];
		}
	}
	static void print(int a[][])
	{
		System.out.println("Good morning");
		int i,j;
		for(i=0;i<9;i++)
		{
			System.out.printf("------------------------------------\n");
			//if(i%3==0)
			  //  printf("---------------------------------------\n");
			for(j=0;j<9;j++)
			{
				System.out.printf(" %d |",a[i][j]);
				//if(j%3==2)
				  //  printf("|");
			}
			System.out.printf("\n");
		}
		System.out.printf("------------------------------------\n\n\n");
	}
}
class Limit extends PlainDocument
{
	private int limit;
    Limit(int limit)
	{
		super();
		this.limit=limit;
	}
	Limit(int limit,boolean upper)
	{
		super();
		this.limit=limit;
	}
	public void insertString(int offset,String str,AttributeSet attr) throws BadLocationException
	{
		if(str==null)
	return;
        if((getLength()+str.length())<=limit)
		{
			super.insertString(offset,str,attr);
		}
			
	}
	
}
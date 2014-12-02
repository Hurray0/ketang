#include <iostream> 
#include <cstdio> 
#include <cstdlib> 
#include <cstring>
#include <string> 
#include <stack> 
// #include <windows.h>
using namespace std;

string original[9]={ //保存原文法 
	"E->E+T", "E->E-T", "E->T", 
	"T->T*F", "T->T/F", "T->F",
	"F->i"  , "F->(E)", "F->n"
};

string G[11]={ //拓广文法 
	"E->TA","A->+TA","A->-TA","A->e",
	"T->FB","B->*FB","B->/FB","B->e",
	"F->(E)","F->i","F->n"
}; 
string FIRST[5]={//first集 
	"E:(in",// E
	"A:+-e",// A
	"T:(in",// T
	"B:*/e",// B
	"F:(in" // F
};
string FOLLOW[5]={//follow集 
	"E:)$",// E
	"A:)$",// A
	"T:+-)$",// T
	"B:+-)$",// B
	"F:+-*/)$",// F
};

char VN[5]={'E','A','T','B','F'};//存储非终结符

char VT[9]={'i','n','+','-','*','/','(',')','$'};//存储终结符

typedef struct Block
{ 
	char vn; //非终结符 
	char vt; //终结符 
	string s;//产生式 
}block;//存储分析预测表每个位置对应的终结符，非终结符，产生式

block table[45];// 5*9=45

char is_vt(char a)//是否为非终结符，不是返回'x'
{
	for(int i=0; i<5; i++)
	{
		if(a==VN[i])
			return 'x';
	}
	if(isalpha(a))//是字母
		return 'i';
	if(isdigit(a))//是数字
		return 'n';
	for(int i=0+2; i<9; i++)
	{
		if(a==VT[i])
			return a;
	}

}


int find_table(char X, char a)//查找是否存在符合的产生式
{
	for(int i=0; i<45; i++)
	{
		if(table[i].vn==X&&table[i].vt==is_vt(a))
		{
			if(0!=table[i].s.size())
			{
				return i;
			}
			else
			{
				return 999;//error项
			}
		}
	}
	return 999;
}

void LL_one(string temp)
{
	stack<char>ST;//栈
	ST.push('$');//初始化压入栈
	ST.push('E');
	cout<<"栈                      "<<"                    输入"<<"     输出"<<endl;//23,20,5
	int i=0, j=1;//temp指针
	char X;

	do
	{

		cout<<j;
		if(j>99)
			cout<<":";
		else
			if(j>9)
				cout<<": ";
			else
				cout<<":  ";
		//输出
		stack<char>t_ST;//栈	
		char t_char;
		int i2=ST.size();
		for(int i1=0; i1<i2; i1++)
		{
			t_char=ST.top();
			t_ST.push(t_char);
			ST.pop();
		}
		for(int i1=0; i1<i2; i1++)
		{
			t_char=t_ST.top();
			t_ST.pop();
			cout<<t_char;
			ST.push(t_char);
		}
		for(int i1=0; i1<23-i2; i1++)
		{
			cout<<" ";
		}
		cout<<"|";
		for(int i1=0; i1<20-temp.size()+i; i1++)
		{
			cout<<" ";
		}
		for(int i1=i; i1<temp.size(); i1++)
			cout<<temp.at(i1);
		cout<<"     ";

		//开始处理
		X=ST.top();
		if('$'==X||'x'!=is_vt(X))//是终结符或S
		{
			if(temp.at(i)==X||(is_vt(temp.at(i))==X))
			{
				ST.pop();
				cout<<"'"<<temp.at(i)<<"'"<<"被接收";
				i++;
			}
			else
			{
				cout<<endl<<endl<<endl<<endl<<"无法接受！"<<endl;
				return;
			}
		}
		else//是非终结符号
		{
			int t_table=find_table(X, temp.at(i));

			if(999!=t_table)
			{
				ST.pop();//弹出X
				int t_i=table[t_table].s.size()-1;
				cout<<table[t_table].s;
				//压栈
				if('e'!=table[t_table].s.at(t_i))//如果不是->e
				{
					while(table[t_table].s.at(t_i)!='>')//压栈
					{
						ST.push(table[t_table].s.at(t_i));
						t_i--;
					}
				}
			}
			else
			{
				cout<<endl<<endl<<endl<<endl<<"无法接受！"<<endl;
				return;
			}
		}
		j++;
		cout<<endl;
	}while('$'!=X);

	cout<<endl<<endl<<"字符串被成功接收！！！"<<endl;
}

int main()
{
	cout<<"1.原文语法为："<<endl;
	for(int i=0; i<=8; i++)
		cout<<original[i]<<endl;
	cout<<endl<<"2.拓广文法为："<<endl;		
	for(int i=0; i<=8; i++)
		cout<<G[i]<<endl;

	cout<<endl<<"3.FIRST集与FOLLOW集："<<endl;		
	for(int i=0; i<5; i++)
	{
		cout<<"FIRST("<<FIRST[i].at(0)<<")={";
		for(int j=2; j<FIRST[i].size(); j++)
		{
			cout<<FIRST[i].at(j)<<", ";
		}
		cout<<"}"<<endl;
	}
	for(int i=0; i<5; i++)
	{
		cout<<"FOLLOW("<<FOLLOW[i].at(0)<<")={";
		for(int j=2; j<FOLLOW[i].size(); j++)
		{
			cout<<FOLLOW[i].at(j)<<", ";
		}
		cout<<"}"<<endl;
	}
	cout<<endl<<"算法4.2 预测分析表的构造:"<<endl<<endl;
	//初始化
	int t=0;
	for(int i=0; i<5; i++)
	{
		for(int j=0; j<9; j++)
		{
			table[t].s.clear();
			table[t].vn=VN[i];
			table[t].vt=VT[j];
			t++;
		}
	}

	//将产生式添加
	string temp;
	char t_a, t_b;
	for(int i=0; i<11; i++)//11个产生式
	{
		temp.clear();
		temp=G[i];//取产生式
		t_a=temp.at(0);
		t_b=temp.at(3);
		if('e'==t_b)//follow集
		{
			int j;
			for( j=0; j<5; j++)//取follow集字符
			{	
				if(t_a==FOLLOW[j].at(0))
					break;
			}
			string t_follow(FOLLOW[j], 2, FOLLOW[j].size()-2);
			for( j=0; j<45; j++)//遍历整个表
			{
				if(table[j].vn==t_a)
				{
					for(int k=0; k<t_follow.size(); k++)
					{
						if(table[j].vt==t_follow.at(k))
						{
							table[j].s.clear();
							table[j].s=temp;
						}
					}
				}
			}
		}
		else//first集
		{
			bool isvn=false;
			for(int k=0; k<5; k++)
			{
				if(t_b==VN[k])
					isvn=true;
			}

			if(isvn)
			{
					//取first集
				int j;
				for( j=0; j<5; j++)//取follow集字符
				{
					if(t_a==FIRST[j].at(0))
						break;
				}
				string t_first(FIRST[j], 2, FIRST[j].size()-2);

				for(int k=0; k<45; k++)
				{
					if(table[k].vn==t_a)
					{
						for(int l=0; l<t_first.size(); l++)
						{
							if(table[k].vt==t_first.at(l))
							{
								table[k].s.clear();
								table[k].s=temp;
							}
						}
					}
				}

			}
			else
			{
				for(int k=0; k<45; k++)
				{
					if(table[k].vt==t_b&&table[k].vn==t_a)
					{
						table[k].s.clear();
						table[k].s=temp;
					}
				}
			}

		}

	}
	//将预测分析表输出
	cout<<"       ";
	for(int i=0; i<9; i++)
	{
		cout<<"   "<<VT[i]<<"   ";
	}
	cout<<endl<<"----------------------------------------------------------------------"<<endl;

	t=0;
	for(int i=0; i<5; i++)
	{
		cout<<VN[i]<<":     ";
		for(int j=0; j<9; j++)
		{
			cout<<table[t].s;
			for(int k=0; k<7-table[t].s.size(); k++)
				cout<<" ";		
			t++;
		}
		cout<<endl;
	}

	//实现算法4.1，构造LL(1)预测分析程序
	cout<<endl<<"实现算法4.1，LL(1)分析字符串";
	cout<<endl<<"请输入待分析字符串：";
	cin>>temp;
	temp.push_back('$');
	LL_one(temp);

	// system("pause");
}
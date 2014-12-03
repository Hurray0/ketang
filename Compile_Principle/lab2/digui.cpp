/**
 *
 * Author: Hurray Zhu
 * Time: 20141202
 * Part: Lexical analysis of Compile System(by Recursion call)
 * web: http://www.ihurray.com
 * E-mail: i@ihurray.com
 * Github: https://github.com/Hurray0/ketang/tree/master/Compile_Principle/lab2
 *
 * 【题目及演算步骤】
 * E->E+T|E-T|T
 * T->T*F|T/F|F
 * F->id|(E)|num
 *
 * 消除左递归有：
 * E->TE`
 * E`->+TE`|-TE`|ε
 * T->FT`
 * T`->*FT`|/FT`|ε
 * F->id|(E)|num
 */

#include <cstdio>
#include <cstring>
 #define clr(a) memset(a,0,sizeof(a))
 #define OK 1
 #define ERROR 0

char input[999];
char buffer[999];
int pointer_input, pointer_buffer, pointer_buffer2;

char nextChar()
{
	return buffer[pointer_buffer2];
}

void forward()
{
	if(buffer[pointer_buffer2] != '\1' && buffer[pointer_buffer2] != '\2')
	{
		printf("字符%c已经处理\n", buffer[pointer_buffer2]);
	}
	else if(buffer[pointer_buffer2] == '\1')
	{
		printf("id已经处理\n");
	}
	else if(buffer[pointer_buffer2] == '\2')
	{
		printf("num已经处理\n");
	}
	pointer_buffer2 ++;
}

int procE();
int procT();
int procF();
int procEE();
int procTT();

int procE()
{
	printf("进入procE\n");
	int TF = 1;
	TF *=procT();
	TF *=procEE();
	printf("退出procE\n");
	return TF;
}

int procEE()
{
	printf("进入procE`\n");
	int TF = 1;
	
	if (nextChar() == '+' || nextChar() == '-')
	{
		forward();
		TF *= procT();
		TF *= procEE();
		printf("退出procE`\n");
		return TF;
	}
	else
	{
		printf("退出procE`\n");
		return TF;
	}
}

int procT()
{
	printf("进入procT\n");
	int TF = 1;
	TF *= procF();
	TF *= procTT();
	printf("退出procT\n");
	return TF;
}

int procTT()
{
	printf("进入procT`\n");
	int TF = 1;
	if (nextChar() == '*' || nextChar() == '/')
	{
		forward();
		TF *= procF();
		TF *= procTT();
		printf("退出procT`\n");
		return TF;
	}
	else
	{
		printf("退出procT`\n");
		return TF;
	}
}

int procF()
{
	printf("进入procF\n");
	int TF = 1;
	if (nextChar() == '\1' || nextChar() == '\2') //id 和 num
	{
		forward();
		printf("退出procF\n");
		return TF;
	}
	else if (nextChar() == '(')
	{
		forward();
		TF *= procE();
		if(nextChar() == ')')
		{
			forward();
			printf("退出procF\n");
			return TF;
		}
		else
		{
			printf("字符'%c'理解出错a！\n", nextChar());
			return ERROR;
		}
	}
	else
	{	
		printf("字符'%c'理解出错b！\n", nextChar());
		return ERROR;
	}
}

int isId()
{
	if((input[pointer_input] > '9' || input[pointer_input] < '0')
		&& input[pointer_input] != '+' && input[pointer_input] != '-'
		&& input[pointer_input] != '*' && input[pointer_input] != '/'
		&& input[pointer_input] != '(' && input[pointer_input] != ')'
		&& input[pointer_input])
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

void id()
{
	int TF = 0;
	while(isId() == 1)
	{
		TF = 1;
		pointer_input ++;
	}
	if(TF == 1)
	{
		buffer[pointer_buffer++] = '\1';
	}
}

int isNum()
{
	if(input[pointer_input] >= '0' && input[pointer_input] <= '9' && input[pointer_input])
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

void num()
{
	int TF = 0;
	while(isNum() == 1)
	{
		TF = 1;
		pointer_input ++;
	}
	if(TF == 1)
	{
		buffer[pointer_buffer++] = '\2';
	}
}

void addToBuffer()
{
	if(isNum())
	{
		num();
	}
	else if(isId())
	{
		id();
	}
	else
	{
		buffer[pointer_buffer] = input[pointer_input];
		pointer_input ++;
		pointer_buffer ++;
	}
}

void printBuffer()
{
	printf("将输入串转换成如下串：");
	for(int i = 0; buffer[i]; i++)
	{
		if(buffer[i] != '\1' && buffer[i] != '\2')
		{
			printf("%c", buffer[i]);
		}
		else if(buffer[i] == '\1')
		{
			printf("id");
		}
		else if(buffer[i] == '\2')
		{
			printf("num");
		}
	}
	printf("\n");
}

int main()
{
	pointer_input = 0;
	pointer_buffer = 0;
	printf("请输入需要匹配的字符串：");
	scanf("%s", input);
	clr(buffer);
	do
	{
		addToBuffer();
	}while(input[pointer_input] != '\0');

	printBuffer();
	pointer_buffer2 = 0;
	int output = procE();
	switch(output)
	{
		case 0:
		{
			printf("字符串%s没有匹配成功！\n", input);
			break;
		}
		case 1:
		{
			printf("字符串%s匹配成功！\n", input);
		}
	}

	// printf("结果为：%d\n", output);

	return 0;
}
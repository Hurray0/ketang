// Project: Lexical analysis
// File: cffx.cpp
// Note: main part
// Author: Hurray Zhu
// Time: 2014.11.10
// E-mail: i@ihurray.com
// Web-site: http://www.ihurray.com
// GitHub: 
 
#include <stdlib.h>  
#include <string.h>  
#include <ctype.h>  
#define NULL 0  

FILE *fp;
char ch;
char *keyword[34] = { "auto", "break", "case", "char", "const", "continue", "default", "do", "double",
"else", "enum", "extern", "float", "for", "goto", "if", "int", "long", "register",
"return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "printf",
"union", "unsigned", "void", "volatile", "while", "main" };//关键字  
char *operatornum[6] = { "+", "-", "*", "/", "++", "--" };//算术运算符  
char *comparison[8] = { "<", "<=", "=", ">", ">=", "<>", "==", "!=" };//比较符  
char *interpunction[8] = { ",", ";", ":=", ".", "(", ")", "{", "}", };//分界符  
char *biaoshifu[6] = { "%", "$", "^", "&", "_", "#" };//特殊标识符  
char *zhushifu[3] = { "//", "/*", "*/" };//注释符  
char *luoji[3] = { "&&", "||", "!" };//逻辑运算符  

bool search(char searchstr[], int wordtype)
{
	int i;
	switch (wordtype)
	{
		case 1:
		for (i = 0; i <= 33; i++)
		{
			if (strcmp(keyword[i], searchstr) == 0)
				return(true);

		}

		case 2:
			for (i = 0; i <= 5; i++)
			{
				if (strcmp(operatornum[i], searchstr) == 0)
					return(true);
			}
			break;

		case 3: 
			for (i = 0; i <= 7; i++)
			{
				if (strcmp(comparison[i], searchstr) == 0)
					return(true);
			}
			break;

		case 4: 
		for (i = 0; i <= 7; i++)
		{
			if (strcmp(interpunction[i], searchstr) == 0)
				return(true);
		}

		break;

		case 5: 
		for (i = 0; i <= 5; i++)
		{
			if (strcmp(biaoshifu[i], searchstr) == 0)
				return(true);
		}
		break;

		case 6: 
		for (i = 0; i <= 2; i++)
		{
			if (strcmp(zhushifu[i], searchstr) == 0)
				return(true);
		}
		break;

		case 7: 
		for (i = 0; i <= 2; i++)
		{
			if (strcmp(luoji[i], searchstr) == 0)
				return(true);
		}
		break;
	}
	return(false);
}

char letterprocess(char ch)//字母处理函数  
{
	int i = -1;
	char letter[20];
	while (isalnum(ch) != 0)
	{
		letter[++i] = ch;
		ch = fgetc(fp);
	};
	letter[i + 1] = '\0';
	if (search(letter, 1))
	{
		printf("【1 保留字, %s】\n", letter);
		//strcat(letter,"\n");  
		//fputs('<' letter '>\n',outp);  
	}
	else
	{
		printf("【2 标识符, %s】\n", letter);
		//strcat(letter,"\n");  
		//fputs(letter,outp);  
	}
	return(ch);
}
 
char numberprocess(char ch)//数字处理程序  
{
	int i = -1;
	char num[20];
	while (isdigit(ch) != 0)
	{
		num[++i] = ch;
		ch = fgetc(fp);
	}
	if (isalpha(ch) != 0)
	{
		while (isspace(ch) == 0)
		{
			num[++i] = ch;
			ch = fgetc(fp);
		}
		num[i + 1] = '\0';
		printf("错误！非法标识符：%s\n", num);
		goto u;
	}
	num[i + 1] = '\0';
	printf("【3 无符号整型数, %s】\n", num);
	//strcat(num,"\n");  
	//fputs(num,outp);  
	u: return(ch);
}
 
char otherprocess(char ch)
{
	int i = -1;
	char other[20];
	if (isspace(ch) != 0)
	{
		ch = fgetc(fp);
		goto u;
	}
	while ((isspace(ch) == 0) && (isalnum(ch) == 0))
	{
		other[++i] = ch;
		ch = fgetc(fp);
	}
	other[i + 1] = '\0';
	if (search(other, 2))
		printf("【4 运算符, %s】\n", other);
	else
		if (search(other, 3))
			printf("【6 运算符, %s】\n", other);
		else
			if (search(other, 4))
				printf("【5 分隔符, %s】\n", other);
			else
				if (search(other, 5))
					printf("【7, %s】\n", other);
				else
					if (search(other, 6))
						printf("【8, %s】\n", other);
					else
						if (search(other, 7))
							printf("【9, %s】\n", other);
						else
							printf("错误！非法字符：%s\n", other);
						u: return (ch);


}

 
int main()
{
	char str, c;
	printf("/******* Author: Hurray ©www.ihurray.com ********/\n");
	printf("(下面是对txt文件中输入串的分析)\n"); 
	if ((fp = fopen("test.txt", "r")) == NULL)
		printf("源程序无法打开！\n");
	else
	{
		str = fgetc(fp);
		while (str != EOF)
		{
			if (isalpha(str) != 0)//判断字符是否为字符  
			{
				str = letterprocess(str);
			}
			else
			{
				if (isdigit(str) != 0)//判断字符是否为数字  
					str = numberprocess(str);
				else
					str = otherprocess(str);
			}
		}
	}
	return 0;
}

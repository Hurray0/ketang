/**
 * 用C语言编写一个用学号查询学生名字的程序，分别用顺序查找和二分法查找算法。之后：
 * 1）画出流程图
 * 2）用两种计算方法，分别计算MacCabe的复杂度，看是否一样
 */

#include <stdio.h>
#include <stdlib.h>

 struct student
 {
 	char id[20];
 	char name[20];
 };

 int compare(char *id1, char *id2)
 {
 	int i;
 	if(id1[0] == '\0')
 		return 1;
 	else if (id2[0] == '\0')
 		return -1;
 	for(i = 0; id1[i] != '\0' && id2[i] != '\0'; i++)
 	{
 		if(id1[i] > id2[i])
 			return 1;
 		else if (id1[i] < id2[i])
 			return -1;
 	}
 	if(id1[i] != '\0') 
 		return 1;
 	else if(id2[i] != '\0')
 		return -1;
 	return 0;
 }

 void sequenceSearch(char *inputID, struct student *s)
 {
 	int i;
 	for(i = 0; s[i].id[0]; i++)
 	{
 		if(compare(s[i].id, inputID) == 0)
 		{
 			printf("顺序查找的姓名为：%s\n", s[i].name);
 			return;
 		}
 	}
 	printf("未查找到！\n");
 }

 void binarySearch(char *inputID, struct student *s, int left, int right)
 {
 	int middle = (left + right)/2;
 	if(compare(inputID, s[middle].id) == 0)
 	{
 		printf("二分查找的姓名为：%s\n", s[middle].name);
 		return;
 	}
 	else if(compare(inputID, s[middle].id) > 0)
 	{
 		if(middle >= right)
 		{
 			printf("未查找到！\n");
 			return;
 		}
 		binarySearch(inputID, s, middle+1, right);
 	}
 	else if(compare(inputID, s[middle].id) < 0)
 	{
 		if(middle <= left)
 		{
 			printf("未查找到！\n");
 			return;
 		}
 		binarySearch(inputID, s, left, middle-1);
 	}
 }

 int cmp(const void *a, const void *b)
 {
 	return compare((*(struct student *)a).id, (*(struct student *)b).id);
 }



 int main()
 {
 	int i;
 	FILE * file = fopen("data.txt","r");
 	struct student s[100];
 	char input[20];

 	for(i = 0; i<100; i++)
 	{
 		s[i].id[0] = '\0';
 	}

 	// freopen("data.txt", "r", stdin);
 	for(i = 0; (fscanf(file, "%s %s", s[i].id, s[i].name)) != EOF; i++)
 	{
 		// printf("%s %s\n", s[i].id, s[i].name);
 	}

 	qsort(s, 100, sizeof(s[0]), cmp);

 	printf("请输入查找的学号：");

 	scanf("%s", input);
 	sequenceSearch(input, s);
 	binarySearch(input, s, 0, i-1);

 	return 0;	
 }
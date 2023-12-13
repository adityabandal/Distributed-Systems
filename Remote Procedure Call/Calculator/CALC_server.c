/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "CALC.h"
#include<stdio.h>

float *
calculate_2_svc(values *argp, struct svc_req *rqstp)
{
	static float  result;

	/*
	 * insert server code here
	 */
	int n1, n2;
	n1 = argp->num1;
	n2 = argp->num2;
	switch(argp->operation){
		case '+': result = n1+n2;
		break;
		case '-': result = n1-n2;
		break;
		case '*': result = n1*n2;
		break;
		case '/': result = n1/n2;
		break;
		default:
		break;

	}

	return &result;
}

/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "CALC.h"
#include<stdio.h>

float
compute_2(char *host, float num1, float num2, char op)
{
	CLIENT *clnt;
	float  *result_1;
	values  calculate_2_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, COMPUTE, COMPUTE_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	calculate_2_arg.num1 = num1;
	calculate_2_arg.num2 = num2;
	calculate_2_arg.operation = op;
	if(op == '/' && num2 == 0){
		printf("Cannot divide by zero!\n");
		return 0;
	}
	result_1 = calculate_2(&calculate_2_arg, clnt);
	if (result_1 == (float *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	return (*result_1);
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	float num1, num2;
	char op;
	printf("Enter number followed by operator:\n");
	scanf("%f %f %c",&num1, &num2, &op);
	float ans = compute_2 (host, num1, num2, op);
	printf("answer: %f\n", ans);
exit (0);
}

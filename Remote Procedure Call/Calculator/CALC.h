/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _CALC_H_RPCGEN
#define _CALC_H_RPCGEN

#include <rpc/rpc.h>


#ifdef __cplusplus
extern "C" {
#endif


struct values {
	float num1;
	float num2;
	char operation;
};
typedef struct values values;

#define COMPUTE 3
#define COMPUTE_VERS 2

#if defined(__STDC__) || defined(__cplusplus)
#define CALCULATE 1
extern  float * calculate_2(values *, CLIENT *);
extern  float * calculate_2_svc(values *, struct svc_req *);
extern int compute_2_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define CALCULATE 1
extern  float * calculate_2();
extern  float * calculate_2_svc();
extern int compute_2_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_values (XDR *, values*);

#else /* K&R C */
extern bool_t xdr_values ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_CALC_H_RPCGEN */

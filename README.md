# ConfCost
This application is able to run various encryption and signature methods and provides timed metrics.

The application was written as part of the practical project "What is the cost of confidentiality?" in Private Enhancing Technologies (PET, WiSe 2016/17) of the technical university Darmstadt.

## Command Line Arguments
The only accepted command line argument is "-p" to specifiy the port to listen on.

## Implementing additional algorithms
To implements additional encryption, signature or key exchange algorithms, consult the instractions found in the Javadoc for 
confcost.controller.algorithm.Encryption, confcost.controller.algorithm.Signature, or confcost.controller.ke.KeyExchange
respectively.
# FunkR-pDAE
SUMMARY
================================================================================

There is a huge project group in the open source community. Developers need to spend a lot of time to find interesting projects from many open source projects, so it is necessary to recommend suitable open source projects for developers. The application of a recommendation system in the field of software engineering can solve this problem. However, developers and project information in the open source community are implicit feedback. In order to fully exploit the information in the open source community and make it suitable for traditional recommendation algorithms, this paper proposes a personalized open source project recommendation method based on auto-encoder, called FunkR-pDAE (Funk singular value decomposition Recommendation approach using pearson correlation coefficient and Double-Auto-Encoders). By taking the Github community as an example, this paper constructs a scoring matrix that represents the developer’s preference for open source projects and a developer relevance matrix based on the characteristic attributes unique to Github. Use the Pearson Correlation Coefficient to calculate developer similarity based on the Developer Relevancy Matrix. Using the score matrix as input, use the auto-encoder to learn feature vectors that represent developers and open source projects. Combining the principle of Funk singular value decomposition, the obtained eigenvectors are converted into a new predictive scoring matrix. At the same time, we define a recommendation formula for Top-N recommendation.

Dataset
==================================================================================
GHTorent retrieves high quality interconnect data through the REST API provided by GitHub. It contains all the public
projects in Github. In this step, we obtain historical developers’ behavior data from the web site. The data includes information
about developers, language information for open source projects, and a series of developers’ behavior about open source projects
(watch, fork, pull-request comment, issue comment). The whole data set found at the following web site:     http://www.ghtorrent.org/downloads.html

PearsonCalcultor
==================================================================================
The step is data collection and preprocessing. From the entire data set, based on the attributes of developers and projects, and the relationships between them, we collect the historical data we need. The development history data collected during this phase includes
attributes such as watch, fork, issue-comment and pull-request comment that represent the association between the developer and the project, the follow attribute that represents the developer relationship. According to the above attribute characteristics, the
matrix D and R are respectively constructed, and the relevance of the developers in the matrix is calculated through the pearson
correlation coefficient.

ModelTraining
==================================================================================
The step is model training and feature vector generation. We call the torch package in a python script via java. We implement the inner product calculation of two scoring vectors. Based on the predictive scoring matrix and developer correlation obtained by the model, we defined a recommended formula. We recommend the corresponding open source projects that may be of interest and return the Top-N projects to the developers.

Instructions
==================================================================================
Some other experimental operations we completed through mysql and excel.

# AutoEncoders

# Importing the libraries
import numpy as np
import pandas as pd
import torch
import torch.nn as nn
import torch.nn.parallel
import torch.optim as optim
import torch.utils.data
from torch.autograd import Variable


# Preparing the training set and the test set
training_set = pd.read_csv('data/u1.base', delimiter = '\t')
training_set = np.array(training_set, dtype = 'int')
test_set = pd.read_csv('data/u1.test', delimiter = '\t')
test_set = np.array(test_set, dtype = 'int')

# Getting the number of developers and projects
nb_developers = int(max(max(training_set[:,0]), max(test_set[:,0])))
nb_projects = int(max(max(training_set[:,1]), max(test_set[:,1])))

# Converting the data into an array with developers in lines and projects in columns
def convert(data):
    new_data = []
    for id_developers in range(1, nb_developers + 1):
        id_projects = data[:,1][data[:,0] == id_developers]
        id_ratings = data[:,2][data[:,0] == id_developers]
        ratings = np.zeros(nb_projects)
        ratings[id_projects - 1] = id_ratings
        new_data.append(list(ratings))
    return new_data
training_set = convert(training_set)
test_set = convert(test_set)

# Converting the data into Torch tensors
training_set = torch.FloatTensor(training_set)
test_set = torch.FloatTensor(test_set)

# Creating the architecture of the Neural Network
class SAE(nn.Module):
    def __init__(self, ):
        super(SAE, self).__init__()
        self.fc1 = nn.Linear(nb_projects, 64)
        self.fc2 = nn.Linear(64, nb_projects)
        self.activation = nn.Sigmoid()
    def forward(self, x):
        x = self.activation(self.fc1(x))
        x = self.fc2(x)
        return x
sae = SAE()
criterion = nn.MSELoss()
optimizer = optim.RMSprop(sae.parameters(), lr = 0.001, weight_decay = 0.5)

# Training the SAE
nb_epoch = 200
for epoch in range(1, nb_epoch + 1):
    train_loss = 0
    s = 0.
    for id_developer in range(nb_developers):
        input = Variable(training_set[id_developer]).unsqueeze(0)
        target = input.clone()
        if torch.sum(target.data > 0) > 0:
            output = sae(input)
            target.require_grad = False
            output[target == 0] = 0
            loss = criterion(output, target)
            mean_corrector = nb_projects/float(torch.sum(target.data > 0) + 1e-10)
            loss.backward()
            train_loss += np.sqrt(loss.data[0]*mean_corrector)
            s += 1.
            optimizer.step()
    print('epoch: '+str(epoch)+' loss: '+str(train_loss/s))

# Testing the SAE
test_loss = 0
s = 0.
for id_developer in range(nb_developers):
    input = Variable(training_set[id_developer]).unsqueeze(0)
    target = Variable(test_set[id_developer])
    if torch.sum(target.data > 0) > 0:
        output = sae(input)
        target.require_grad = False
        output[target == 0] = 0
        loss = criterion(output, target)
        mean_corrector = nb_projects/float(torch.sum(target.data > 0) + 1e-10)
        test_loss += np.sqrt(loss.data[0]*mean_corrector)
        s += 1.
print('test loss(RMSE): '+str(test_loss/s))
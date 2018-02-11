function [] = AdaptiveDecisionBoundaries()
% Adaptive Decision Boundary. (feature size = 2)
%  Input: number of samples, features, class labels. Output: decision boundary
%    Md. Farhan Sadique. Student ID: 130238. Khulna University.
%% 
fileID = fopen('samples.txt','r');
formatSpec = '%i';
input = fscanf(fileID,formatSpec);   %reading samples from file
numOfSamples = input(1);
x1 = zeros(1, numOfSamples);
x2 = zeros(1, numOfSamples);
labels = zeros(1, numOfSamples);
k = 2;
for i = 1:numOfSamples
    x1(i) = input(k);
    x2(i) = input(k+1);
    labels(i) = input(k+2);
    k = k + 3;
end
%Adaptive decision boundaries
w0 = 5;
w1 = 5;
w2 = 5;
c = 1;
k = 1;
iterations = 0;
error = 1;
fprintf('t|i|x1|x2|old w0|old w1|old w2|D|Error?|new w0|new w1|new w2\n');
fprintf('.............................................................\n');
while error == 1
    numOfMissclassification = 0;
    for i = 1:numOfSamples
        iterations = iterations + 1;
        fprintf('%i ',iterations);
        fprintf('%i %i %i %i %i ',i,x1(i),x2(i),w0,w1,w2);
        D = w0 + w1 * x1(i) + w2 * x2(i);
        fprintf('%i ',D);   
        if (D < 0 && labels(i) == 1)||(D >= 0 && labels(i) == -1)          %misclassified
            fprintf('Yes ');
            w0 = w0 + c * labels(i) * k;
            w1 = w1 + c * labels(i) * x1(i);
            w2 = w2 + c * labels(i) * x2(i);
            numOfMissclassification = numOfMissclassification + 1;
        else 
            fprintf('No ');
        end
        fprintf('%i %i %i\n',w0,w1,w2);
    end
    if numOfMissclassification == 0    %all classifications are correct
        error = 0; 
    end
end
%output: decision boundary
syms func x1 x2;
func = w0 + w1 * x1 + w2 * x2 == 0;
fprintf('The decision boundary: ');
disp(func);
end

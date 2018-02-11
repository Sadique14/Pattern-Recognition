function [] = MinimumSquareError()
% Minimum Square Error Classification. (feature size = 2)
%  Input: number of samples, features, class labels. Output: decision boundary
%    Md. Farhan Sadique. Student ID: 130238. Khulna University.
%% 
fileID = fopen('samples.txt','r');
formatSpec = '%i';
input = fscanf(fileID,formatSpec);   %reading samples from file
numOfSamples = input(1);
x1 = zeros(1, numOfSamples);
x2 = zeros(1, numOfSamples);
d = zeros(1, numOfSamples);
k = 2;
for i = 1:numOfSamples
    x1(i) = input(k);
    x2(i) = input(k+1);
    d(i) = input(k+2);
    k = k + 3;
end
%Minumum square error procedure
syms f(x) E(x) w0 w1 w2;
E(x) = 0;
for i=1:numOfSamples
    f(x) = w0 + w1 * x1(i) + w2 * x2(i);
    E(x) = E(x) +(f(x) - d(i))^2;           %sum of the squared errors
end
dfW0 = diff(E,w0);      %derivatives of E w.r.t. w0
dfW1 = diff(E,w1);      %derivatives of E w.r.t. w1
dfW2 = diff(E,w2);      %derivatives of E w.r.t. w2
eqn1 =  dfW0 == 0;
eqn2 =  dfW1 == 0;
eqn3 =  dfW2 == 0;
sol = solve([eqn1, eqn2, eqn3], [w0, w1, w2]);    %solving
solW0 = sol.w0;
solW1 = sol.w1;
solW2 = sol.w2;
%output: decision boundary
fprintf('w0 = %f, w1 = %f, w2 = %f\n',solW0,solW1,solW2);
syms func x1 x2;
func = solW0 + solW1 * x1 + solW2 * x2 == 0;
fprintf('The decision boundary: ');
disp(func);
end
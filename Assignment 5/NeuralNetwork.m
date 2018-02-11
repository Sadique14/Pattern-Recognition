function [] = NeuralNetwork()
%O = positive class
%X = negative class
% features = feature_extractor(test);
% disp(size(features));
imagefiles = dir('O/*.png');   
imagefiles2 = dir('X/*.png'); 
numOfSamples = length(imagefiles);
index = 1;
for i=1:numOfSamples
    currentfilename = imagefiles(i).name;
    currentImagePath = strcat('O/',currentfilename);
    I = imread(currentImagePath);
    features(index,:) = feature_extractor(I);
    labels(index) = 1;
    index = index + 1;
    currentfilename = imagefiles2(i).name;
    currentImagePath = strcat('X/',currentfilename);
    I = imread(currentImagePath);
    features(index,:) = feature_extractor(I);
    labels(index) = -1;
    index = index + 1;
end
[x,numOfFeatures,z] = size(features);
%Adaptive decision boundaries
c = 1;
k = 1;
w0 = 0;
for i=1:numOfFeatures
    w(i) = 0;
end
numOfMissclassification = 0;
iterations = 0;
error = 1;
% fprintf('t|i|x1|x2|old w0|old w1|old w2|D|Error?|new w0|new w1|new w2\n');
% fprintf('.............................................................\n');
while error == 1
    iterations = iterations + 1;
    numOfMissclassification = 0;
    for i = 1:numOfSamples
        D = w0;
        for j = 1:numOfFeatures
            D = D + w(j) * features(i,j);
        end  
        if (D < 0 && labels(i) == 1)||(D >= 0 && labels(i) == -1)          
            w0 = w0 + c * labels(i) * k;
            for j = 1:numOfFeatures
                w(j) = w(j) + c * labels(i) * features(i,j);
            end
            numOfMissclassification = numOfMissclassification + 1;
        end
    end
    if numOfMissclassification == 0 
        error = 0; 
    end
end
fprintf('...Training Completed...\n');
fprintf('........Testing.........\n');
test = imread('O1.png');
testFeatures = feature_extractor(test);
D = w0;
for j = 1:numOfFeatures
    D = D + w(j) * testFeatures(j,1);
end
if D >= 0
    fprintf('This is O');
else
    fprintf('This is X');
end
end


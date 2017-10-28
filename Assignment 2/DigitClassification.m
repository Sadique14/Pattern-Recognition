%%
%recognition method: the k Nearest Neighbors (knn) classifier.
%Training dataset: characters2, Test dataset: characters4, (1 stroke).
%Md. Farhan Sadique
%Student ID: 130238
%%
function [] = DigitClassification()
prompt = 'value of k: ';
k = input(prompt);
originalDataset = load('characters4.mat'); %training
originalDataset2 = load('characters2.mat');  %test
trainingDataset = originalDataset.Chars1Stroke; %taking features of training
testDataset = originalDataset2.Chars1Stroke; 
trainingNames = originalDataset.Labels1StrokeChars;
testNames = originalDataset2.Labels1StrokeChars;
trainingDataset = Decimate(trainingDataset,1,5);
testDataset = Decimate(testDataset,1,5);
[numOfDigitsOfTrainingDataset,y1,z1] = size(trainingDataset);
[numOfDigitsOfTestDataset,y2,z2] = size(testDataset);
for i = 1:z1
    trainingDataset(:,:,i) = Centralize(trainingDataset(:,:,i));
    trainingDataset(:,:,i) = NormalizeSize(trainingDataset(:,:,i));
end
for i = 1:z2
    testDataset(:,:,i) = Centralize(testDataset(:,:,i));
    testDataset(:,:,i) = NormalizeSize(testDataset(:,:,i));
end

%k-nearest neighbor classifiers
distances = zeros(1,10);
correct = 0;
wrong = 0;
% disp(trainingNames(3));
for i = 1:numOfDigitsOfTestDataset          %for every tuple of test dataset
    for j = 1:numOfDigitsOfTrainingDataset    %with every training tuple
        distances(j) = dtw(trainingDataset(j,:,:),testDataset(i,:,:),50);
    end
    [sortedDist oldIndex] = sort(distances);  %sorting according to distances
%     disp(size(distances));
    tempNames = zeros(1,k);
    for m = 1:k   
        tempNames(m) = trainingNames(oldIndex(m));
    end
    tempTraining = mode(tempNames);    %taking the class with max occurence
    if tempTraining == testNames(i)
        correct = correct + 1;
    else
        wrong = wrong + 1;
    end
end
disp(' ');
fprintf('Total correct result %i\n',correct);
fprintf('Total wrong result %i\n',wrong);
errorRate = (wrong/numOfDigitsOfTestDataset)*100;
fprintf('Total error rate %f\n',errorRate);
end
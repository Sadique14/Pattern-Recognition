%%
%recognition method: the k Nearest Neighbors (knn) classifier.
%Training dataset: divided characters4, Test dataset: divided characters4, (1 stroke).
%Md. Farhan Sadique
%Student ID: 130238
%%
function [] = DigitClassification2()
prompt = 'value of k: ';
k = input(prompt);
originalDataset = load('characters4.mat'); %load dataset
[S1A,S1B,S2A,S2B,L1A,L1B,L2A,L2B] = DivideSetsLottery(originalDataset.Chars1Stroke,originalDataset.Chars2Stroke,originalDataset.Labels1StrokeChars,originalDataset.Labels2StrokeChars,5);
% disp((L1A));
trainingDataset = S1B;
testDataset = S1A;
trainingNames = L1B;
testNames = L1A;
trainingDataset = Decimate(trainingDataset,1,2);
testDataset = Decimate(testDataset,1,2);
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
for i = 1:numOfDigitsOfTestDataset
    for j = 1:numOfDigitsOfTrainingDataset
        distances(j) = dtw(trainingDataset(j,:,:),testDataset(i,:,:),50);
    end
%      disp(distances);
    [sortedDist oldIndex] = sort(distances);  %sorting according to distances
%     disp(size(distances));
    tempNames = zeros(1,k);
    for m = 1:k   
        tempNames(m) = trainingNames(oldIndex(m));
    end
    tempTraining = mode(tempNames);
    if tempTraining == testNames(i)
        correct = correct + 1;
    else
        wrong = wrong + 1;
    end
end
fprintf('Total correct result %i\n',correct);
fprintf('Total wrong result %i\n',wrong);
errorRate = (wrong/numOfDigitsOfTestDataset)*100;
fprintf('Total error rate %f\n',errorRate);
end
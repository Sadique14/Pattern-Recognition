function [] = Training_0()
k = 3;
originalDataset = load('characters2.mat');
testDataset = originalDataset.Chars1Stroke;
names = originalDataset.Labels1StrokeChars;
testDataset = Decimate(testDataset,1,3);
[numOfDigits,y,z] = size(testDataset);
for i = 1:z
    testDataset(:,:,i) = Centralize(testDataset(:,:,i));
    testDataset(:,:,i) = NormalizeSize(testDataset(:,:,i));
end
%setting up the training dataset. Choosing the first occurence of every
%number.
% trainingDataset = zeros(10,y,z);
tIn = zeros(1,10);
for i = 0:9
    for j = 1:numOfDigits
        if names(1,j) == i
            trainingDataset(i+1,:,:) = testDataset(j,:,:);
            trainingNames(i+1) = names(j);
            tIn(i+1) = j;
            break;
        else
            trainingNames(i+1) = -1;
        end
    end
end
% disp(tariningNames);
%removing training data from test dataset
testDataset([tIn(1),tIn(2),tIn(3),tIn(4),tIn(5),tIn(7),tIn(9),tIn(10)],:,:) = [];
names([tIn(1),tIn(2),tIn(3),tIn(4),tIn(5),tIn(7),tIn(9),tIn(10)]) = [];
[numOfDigits,y,z] = size(testDataset);
%  disp(size(names));
%5 and 7 are not present in the dataset
% for i = 1:10
%     if tIn(i) ~= 0
%         dataset(tIn(i),:,:) = [];
%         names(tIn(i)) = [];
%     end
% end
% disp((names));
% disp(index(5));
% disp(names(index(4)));
%k-nearest neighbor classifiers
% for i = 0:9
%     for j = 1:numOfDigits
%         if i ~= j
%             distance(j) = dtw(dataset(index(i+1),:,:),dataset(j,:,:),50);
%         else
%             distance(j) = 1000;  %don't want to compare with training data
%         end
%     end
%     [sortedDist index] = sort(distance);  %sorting according to distances
%     for m = 1:k            %k nearest neighbor
%         
%     end
% end
%k-nearest neighbor classifiers
distances = zeros(1,10);
correct = 0;
wrong = 0;
% disp(trainingNames(3));
%  disp(names(1));
% disp(dtw(testDataset(tIn(5),:,:),testDataset(1,:,:),50));
for i = 1:5
    for j = 0:9
        if trainingNames(j+1) ~= -1    %means this digit is not present in the dataset
            distances(j+1) = dtw(trainingDataset(j+1,:,:),testDataset(i,:,:),50);
        else
            distances(j+1) = 999999.0;  
        end
    end
%     disp(distances);
    [sortedDist oldIndex] = sort(distances);  %sorting according to distances
%     disp(sortedDist);
    disp(trainingNames(oldIndex(1))); %this is training class
    disp(names(i)); %this is the test class
    for m = 1:k   
        tempNames(m) = trainingNames(oldIndex(m));
    end
    tempTraining = mode(tempNames);
    if tempTraining == names(i)
        correct = correct + 1;
    else
        wrong = wrong + 1;
    end
end
disp(correct);
disp(wrong);
end
   
